package com.digitalHouse.beerClub.service.interfaces;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.EntityInactiveException;
import com.digitalHouse.beerClub.exceptions.InsufficientBalanceException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.model.CardPayment;
import com.digitalHouse.beerClub.model.Payment;
import com.digitalHouse.beerClub.model.PaymentStatus;
import com.digitalHouse.beerClub.model.User;
import com.digitalHouse.beerClub.model.dto.PaymentApplicationDTO;
import com.digitalHouse.beerClub.model.dto.PaymentDTO;

import java.util.List;

public interface IPaymentService {

        List<PaymentDTO> searchAll();
        List<PaymentDTO> findPaymentsByUserId(Long userId);
        PaymentDTO searchById(Long id) throws NotFoundException;
        Payment savePayment(CardPayment card, User user) throws NotFoundException, InsufficientBalanceException;
        void paymentValidation(Long subscriptionId, String cardHolder, String cardNumber,String cvv, String expDate ) throws EntityInactiveException, NotFoundException, BadRequestException, InsufficientBalanceException;
        PaymentDTO updatePaymentStatus(Long paymentId, PaymentStatus newStatus) throws NotFoundException;
}
