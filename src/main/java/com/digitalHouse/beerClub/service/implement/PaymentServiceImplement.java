package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.config.MyAppConfig;
import com.digitalHouse.beerClub.exceptions.*;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.*;
import com.digitalHouse.beerClub.model.dto.*;
import com.digitalHouse.beerClub.repository.IPaymentRepository;
import com.digitalHouse.beerClub.repository.ISubscriptionRepository;
import com.digitalHouse.beerClub.repository.IUserRepository;
import com.digitalHouse.beerClub.service.interfaces.IPaymentService;
import com.digitalHouse.beerClub.utils.AccountUtils;
import com.digitalHouse.beerClub.utils.CardUtils;
import com.digitalHouse.beerClub.utils.TransformationUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class PaymentServiceImplement implements IPaymentService {

    private final IPaymentRepository paymentRepository;
    private final ISubscriptionRepository subscriptionRepository;
    private final CardService cardService;
    private final AccountService accountService;
    private final Mapper paymentMapper;
    private final MyAppConfig myAppConfig;
    private final IUserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    public PaymentServiceImplement(IPaymentRepository paymentRepository, ISubscriptionRepository subscriptionRepository, CardService cardService, AccountService accountService, Mapper mapper, MyAppConfig myAppConfig, IUserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.cardService = cardService;
        this.accountService = accountService;
        this.paymentMapper = mapper;
        this.myAppConfig = myAppConfig;
        this.userRepository = userRepository;
    }

    // Método para que el administrador pueda acceder a todos los pagos.
    @Override
    public List<PaymentDTO> searchAll() {
        return paymentRepository.findAll().stream().map(payment -> paymentMapper.converter(payment, PaymentDTO.class)).toList();
    }

    // Método para que el administrador pueda acceder a los pagos de un usuario por su id.
    @Override
    public List<PaymentDTO> findPaymentsByUserId(Long userId) {
        return paymentRepository.findByUserId(userId).stream().map(payment -> paymentMapper.converter(payment, PaymentDTO.class)).toList();
    }

    // Método para que el usuario autenticado pueda acceder a su pago por id.
    @Override
    public PaymentDTO searchById(Long id, Authentication authentication) throws NotFoundException, ForbiddenException {
        User user = userRepository.findByEmail(authentication.getName());

        if (!user.isActive()) {
            throw new ForbiddenException("El usuario no está activo");
        }
        Payment payment = paymentRepository.findById(id).orElseThrow(() -> new NotFoundException("No se encontró el pago con ID: " + id));

        if (payment.getUserId().equals(user.getId())) {
            return paymentMapper.converter(payment, PaymentDTO.class);
        } else {
            throw new ForbiddenException("El pago no pertenece al usuario autenticado");
        }
    }

    // Método para que el usuario autenticado pueda acceder a todos sus pagos.
    @Override
    public List<PaymentDTO> getPaymentsUserAuth(Authentication authentication) throws NotFoundException, ForbiddenException {
        User user =  userRepository.findByEmail(authentication.getName());

        if (!user.isActive()) {
            throw new ForbiddenException("El usuario no está activo");
        }

        List<Payment> userPayments = paymentRepository.findByUserId(user.getId());

        if (userPayments.isEmpty()) {
            throw new NotFoundException("No se encontraron pagos para el usuario con ID: " + user.getId());
        }

        return userPayments.stream()
                .map(payment -> paymentMapper.converter(payment, PaymentDTO.class))
                .toList();
    }

    // Método para crear un pago cuando el usuario se registra por 1º vez.
    @Transactional
    @Override
    public Payment savePayment(CardPayment card, User user) throws NotFoundException, InsufficientBalanceException {
        String accountBeerClubNumber = myAppConfig.getAccountNumber();
        Subscription subscription = subscriptionRepository.findById(user.getSubscription().getId()).orElseThrow(() -> new NotFoundException("No se encontró la suscripción"));

        Double amount = subscription.getPrice();
        String description = subscription.getName();
        String invoiceNumber = AccountUtils.getInvoiceNumber();

        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setDescription(description);
        payment.setDate(LocalDateTime.now());
        payment.setCardNumber(CardUtils.getLastFourDigits(card.getNumber()));
        payment.setUserId(user.getId());
        payment.setInvoiceNumber(invoiceNumber);
        payment.setSubscription(subscription);

        CardDTO cardDTO = cardService.searchByCardNumber(card.getNumber());

        if(cardDTO.getCardType().equals(CardType.DEBIT)) {
            Long accountId = cardDTO.getAccountId();
            accountService.debit(accountId, amount);
        }else {
            cardService.cardDebit(cardDTO.getId(), amount);
        }

        accountService.addCredit(accountBeerClubNumber, amount);

        payment.setStatus(PaymentStatus.APROBADO);
        payment.setPaymentDate(LocalDateTime.now());
        paymentRepository.save(payment);

        return payment;
    }

    @Override
    public void paymentValidation(Long subscriptionId, String cardHolder, String cardNumber,String cvv, String expDate ) throws EntityInactiveException, NotFoundException, BadRequestException, InsufficientBalanceException {
        String accountBeerClubNumber = myAppConfig.getAccountNumber();
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(() -> new NotFoundException("Subscription not found"));

        Double amount = subscription.getPrice();
        cardValidation(cardHolder, cardNumber, TransformationUtils.getNumber(cvv), expDate);

        CardDTO cardDTO = cardService.searchByCardNumber(cardNumber);
        CardType cardType = cardDTO.getCardType();

        if(cardType.equals(CardType.CREDIT)) {
            if(cardDTO.getCreditLimit() < amount ){
                throw new InsufficientBalanceException("La tarjeta no tiene crédito suficiente");
            }
        }else {
            Long accountId = cardDTO.getAccountId();
            accountValidation(accountId, amount);
        }

        try{
            accountService.searchByAccountNumber(accountBeerClubNumber);
        }catch (NotFoundException e) {
            throw new NotFoundException("La cuenta de destino no está disponible");
        }

    }

    // Método para el administrador pueda enviar a todos los usuarios activos  y con rol USER la factura de pago del mes correspondiente.
    @Override
    public List<PaymentDTO> createPaymentsAndSendInvoices() {
        List<User> activeUsers = userRepository.findByActiveTrue();

        List<User> activeUsersWithUserRole = activeUsers.stream()
                .filter(user -> user.getRole() == RoleType.USER)
                .toList();

        String emailType = "invoice";

        return activeUsersWithUserRole.stream()
                .map(user -> {
                    try {
                        PaymentDTO paymentDTO = createPaymentInvoice(user);
                        sendEmail(user, paymentDTO, emailType);
                        return paymentDTO;
                    } catch (NotFoundException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .filter(Objects::nonNull) // Filtra los resultados nulos (caso de excepción)
                .toList();
    }

    // Método para crear una factura de pago para usuarios ya registrados.
    private PaymentDTO createPaymentInvoice(User user) throws NotFoundException {
        Payment payment = searchPaymentByMonthYear(user);

        if(payment != null) {
            System.out.println("Ya se registró una factura de pago para este usuario en el mes actual.");
            return paymentMapper.converter(payment, PaymentDTO.class);
        } else {
            Subscription subscription = subscriptionRepository.findById(user.getSubscription().getId()).orElseThrow(() -> new NotFoundException("No se encontró la suscripción"));
            Double amount = subscription.getPrice();
            String description = subscription.getName();
            String invoiceNumber = AccountUtils.getInvoiceNumber();

            Payment newPayment = new Payment();
            newPayment.setAmount(amount);
            newPayment.setDescription(description);
            newPayment.setUserId(user.getId());
            newPayment.setInvoiceNumber(invoiceNumber);
            newPayment.setDate(LocalDateTime.now());
            newPayment.setSubscription(subscription);

            paymentRepository.save(newPayment);
            return paymentMapper.converter(newPayment, PaymentDTO.class);
        }
    }

    private Payment searchPaymentByMonthYear(User user) {
        int currentYear = LocalDateTime.now().getYear();
        int currentMonth = LocalDateTime.now().getMonthValue();
        return paymentRepository.findPaymentByUserIdAndMonthYear(user.getId(), currentYear, currentMonth);
    }

    private void sendEmail(User user, PaymentDTO paymentDTO, String emailType) throws NotFoundException {
        String to = user.getEmail();
        String username = user.getFirstName() + " " + user.getLastName();
        String invoice = paymentDTO.getInvoiceNumber();
        String amount = String.valueOf(paymentDTO.getAmount());
        String description = paymentDTO.getDescription();
        String state = String.valueOf(paymentDTO.getPaymentStatus());

        if ("invoice".equals(emailType)) {
            if (isInvoiceAlreadySent(user)) {
                System.out.println("La factura ya se ha enviado para este usuario en el mes actual.");
                return;
            }
            Subscription subscription = user.getSubscription();
            String content = emailService.buildContentPaymentEmail(username, invoice, amount, description, state);
            emailService.sendHtmlMessage(to, "Factura de Pago", content);

            // Actualiza la fecha de envío de la factura
            Payment payment = paymentRepository.findById(paymentDTO.getId()).orElseThrow(() -> new NotFoundException("No se encontró el pago con id" + paymentDTO.getId()));
            payment.setInvoiceDate(LocalDateTime.now());
            paymentRepository.save(payment);

        } else if("payment".equals(emailType)) {
            String  content = emailService.buildContentPaymentTransactionEmail(username, invoice, amount, description, state);
            emailService.sendHtmlMessage(to, "Notificación de Transacción Beer Club", content);
        }
    }

    private boolean isInvoiceAlreadySent(User user) {
        int currentYear = LocalDateTime.now().getYear();
        int currentMonth = LocalDateTime.now().getMonthValue();
        return paymentRepository.existsInvoiceByUserId(user.getId(), currentYear, currentMonth);
    }

    // Método para terminar de procesar el pago luego de que el usuario recibiera el link de la factura.
    @Override
    public PaymentDTO processPayment(PaymentApplicationDTO paymentDTO) throws NotFoundException, InsufficientBalanceException, EntityInactiveException, BadRequestException {
        String accountBeerClubNumber = myAppConfig.getAccountNumber();
        Payment payment = paymentRepository.findById(paymentDTO.getPaymentId()).orElseThrow(() -> new NotFoundException("No se encontró el pago"));

        User user = userRepository.findById(payment.getUserId()).orElseThrow(() -> new NotFoundException("No se encontró el usuario con ID: " + payment.getUserId()));
        PaymentDTO newPaymentDTO;
        String emailType = "payment";

        try {
            this.paymentValidation(payment.getSubscription().getId(), paymentDTO.getCardHolder(), paymentDTO.getCardNumber(), paymentDTO.getCvv(), paymentDTO.getExpDate());

            payment.setPaymentDate(LocalDateTime.now());
            payment.setCardNumber(CardUtils.getLastFourDigits(paymentDTO.getCardNumber()));

            Double amount = payment.getSubscription().getPrice();
            CardDTO cardDTO = cardService.searchByCardNumber(paymentDTO.getCardNumber());

            if (cardDTO.getCardType().equals(CardType.DEBIT)) {
                Long accountId = cardDTO.getAccountId();
                accountService.debit(accountId, amount);
            } else {
                cardService.cardDebit(cardDTO.getId(), amount);
            }

            accountService.addCredit(accountBeerClubNumber, amount);

            payment.setStatus(PaymentStatus.APROBADO);
            paymentRepository.save(payment);
            newPaymentDTO =  paymentMapper.converter(payment, PaymentDTO.class);

            sendEmail(user, newPaymentDTO, emailType);
            return newPaymentDTO;

        } catch (NotFoundException | InsufficientBalanceException | EntityInactiveException | BadRequestException e) {
            payment.setStatus(PaymentStatus.RECHAZADO);
            paymentRepository.save(payment);
            newPaymentDTO =  paymentMapper.converter(payment, PaymentDTO.class);
            sendEmail(user, newPaymentDTO, emailType);
            throw e;
        }
    }

    // Método para que el administrador pueda cambiar el estado de un pago.
    @Override
    public PaymentDTO updatePaymentStatus(Long paymentId, PaymentStatus newStatus) throws NotFoundException {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new NotFoundException("Payment not found with ID: " + paymentId));
        payment.setStatus(newStatus);
        paymentRepository.save(payment);
        return paymentMapper.converter(payment, PaymentDTO.class);
    }

    private void cardValidation(String cardHolder, String number, int cvv, String expDate) throws NotFoundException, EntityInactiveException, BadRequestException {
        CardDTO cardDTO = cardService.searchByCardNumber(number);
        LocalDate expDateTime = CardUtils.parseStringToLocalDate(expDate);

        if(!cardDTO.getIsActive()) {
            throw new EntityInactiveException("La tarjeta no está activa");
        }
        if(!expDateTime.equals(cardDTO.getExpirationDate())) {
            throw new BadRequestException("La fecha de vencimiento de la tarjeta no es correcta");
        }
        if(!cardHolder.equals(cardDTO.getCardHolderName())) {
            throw new BadRequestException("El nombre del titular de la tarjeta no coincide");
        }
        if(cvv != cardDTO.getCvv()) {
            throw new BadRequestException("El cvv de la tarjeta no es correcto");
        }

    }

    private void accountValidation(Long accountId, Double amount) throws NotFoundException, EntityInactiveException, InsufficientBalanceException {
        AccountDTO accountDTO = accountService.searchById(accountId);
        if(!accountDTO.getIsActive()) {
            throw new EntityInactiveException("La cuenta no está activa");
        }
        if(accountDTO.getBalance() < amount) {
            throw new InsufficientBalanceException("La cuenta no tiene saldo suficiente");
        }

    }
}

