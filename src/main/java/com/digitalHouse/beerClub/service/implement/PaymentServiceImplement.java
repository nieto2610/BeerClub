
package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.config.MyAppConfig;
import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.EntityInactiveException;
import com.digitalHouse.beerClub.exceptions.InsufficientBalanceException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.CardPayment;
import com.digitalHouse.beerClub.model.Payment;
import com.digitalHouse.beerClub.model.Subscription;
import com.digitalHouse.beerClub.model.User;
import com.digitalHouse.beerClub.model.dto.*;
import com.digitalHouse.beerClub.repository.IPaymentRepository;
import com.digitalHouse.beerClub.repository.ISubscriptionRepository;
import com.digitalHouse.beerClub.service.interfaces.IPaymentService;
import com.digitalHouse.beerClub.utils.AccountUtils;
import com.digitalHouse.beerClub.utils.CardUtils;
import com.digitalHouse.beerClub.utils.TransformationUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImplement implements IPaymentService {

    private final IPaymentRepository paymentRepository;
    private final ISubscriptionRepository subscriptionRepository;
    private final CardService cardService;
    private final AccountService accountService;
    private final Mapper transactionMapper;
    private final MyAppConfig myAppConfig;

    @Autowired
    public PaymentServiceImplement(IPaymentRepository paymentRepository, ISubscriptionRepository subscriptionRepository, CardService cardService, AccountService accountService, Mapper mapper, MyAppConfig myAppConfig) {
        this.paymentRepository = paymentRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.cardService = cardService;
        this.accountService = accountService;
        this.transactionMapper = mapper;
        this.myAppConfig = myAppConfig;
    }
    @Override
    public List<PaymentDTO> searchAll() {
        return null;
    }

    @Override
    public PaymentDTO searchById(Long id) throws NotFoundException {
        return null;
    }

    @Transactional
    @Override
    public Payment savePayment(CardPayment card, User user) throws NotFoundException, InsufficientBalanceException {
        //Long accountBeerClubId = 1L;
        String accountBeerClubNumber = myAppConfig.getAccountNumber();
        Subscription subscription = subscriptionRepository.findById(user.getSubscription().getId()).orElseThrow(() -> new NotFoundException("Subscription not found"));

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

        return paymentRepository.save(payment);
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
