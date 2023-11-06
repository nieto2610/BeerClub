
package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.EntityInactiveException;
import com.digitalHouse.beerClub.exceptions.InsufficientBalanceException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.Payment;
import com.digitalHouse.beerClub.model.Subscription;
import com.digitalHouse.beerClub.model.dto.AccountDTO;
import com.digitalHouse.beerClub.model.dto.CardDTO;
import com.digitalHouse.beerClub.model.dto.PaymentApplicationDTO;
import com.digitalHouse.beerClub.model.dto.PaymentDTO;
import com.digitalHouse.beerClub.repository.IPaymentRepository;
import com.digitalHouse.beerClub.repository.ISubscriptionRepository;
import com.digitalHouse.beerClub.service.interfaces.IPaymentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

public class PaymentServiceImplement implements IPaymentService {

    private final IPaymentRepository paymentRepository;
    private final ISubscriptionRepository subscriptionRepository;
    private final CardService cardService;
    private final AccountService accountService;
    private final Mapper transactionMapper;

    @Autowired
    public PaymentServiceImplement(IPaymentRepository paymentRepository, ISubscriptionRepository subscriptionRepository, CardService cardService, AccountService accountService, Mapper mapper) {
        this.paymentRepository = paymentRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.cardService = cardService;
        this.accountService = accountService;
        this.transactionMapper = mapper;
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
    public PaymentDTO savePayment(PaymentApplicationDTO paymentApplicationDTO) throws NotFoundException, EntityInactiveException, BadRequestException, InsufficientBalanceException {
        Long accountBeerClubId = 1L;

        Subscription subscription = subscriptionRepository.findById(paymentApplicationDTO.getSubscriptionId()).orElseThrow(() -> new NotFoundException("Subscription not found"));

        Double amount = subscription.getPrice();
        String description = subscription.getName();
        String cardHolder = paymentApplicationDTO.getCardHolder();
        String cardNumber = paymentApplicationDTO.getCardNumber();
        String expDate = paymentApplicationDTO.getExpDate();
        int cvv = paymentApplicationDTO.getCvv();

        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setDescription(description);
        payment.setDate(LocalDateTime.now());
        payment.setCardNumber(cardNumber);
        payment.setSubscription(subscription);

        cardValidation(cardHolder, cardNumber, cvv, expDate);

        CardDTO cardDTO = cardService.searchByCardNumber(cardNumber);
        Long accountId = cardDTO.getAccountId();

        accountValidation(accountId, amount);

        accountService.debit(accountId,amount);
        accountService.credit(accountBeerClubId, amount);
        return null;
    }

    private void cardValidation(String cardHolder, String number, int cvv, String expDate) throws NotFoundException, EntityInactiveException, BadRequestException {
        CardDTO cardDTO = cardService.searchByCardNumber(number);
        if(!cardDTO.getIsActive()) {
            throw new EntityInactiveException("The card is not actived");
        }
        if(!expDate.equals(cardDTO.getExpirationDate())) {
            throw new BadRequestException("The expDate is wrong");
        }
        if(!cardHolder.equals(cardDTO.getCardHolderName())) {
            throw new BadRequestException("The cardHolder is wrong");
        }
        if(cvv != cardDTO.getCvv()) {
            throw new BadRequestException("The cvv is wrong");
        }
    }

    private void accountValidation(Long accountId, Double amount) throws NotFoundException, EntityInactiveException, InsufficientBalanceException {
        AccountDTO accountDTO = accountService.searchById(accountId);
        if(!accountDTO.getIsActive()) {
            throw new EntityInactiveException("The account is not actived");
        }
        if(accountDTO.getBalance() < amount) {
            throw new InsufficientBalanceException("The balance is lower than the amount");
        }
    }
}

