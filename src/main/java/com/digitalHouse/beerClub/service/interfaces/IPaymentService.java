package com.digitalHouse.beerClub.service.interfaces;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.EntityInactiveException;
import com.digitalHouse.beerClub.exceptions.InsufficientBalanceException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.model.dto.PaymentApplicationDTO;
import com.digitalHouse.beerClub.model.dto.PaymentDTO;

import java.util.List;

public interface IPaymentService {

        List<PaymentDTO> searchAll();
        PaymentDTO searchById(Long id) throws NotFoundException;
        PaymentDTO savePayment(Long subscriptionId, String cardHolder, String cardNumber,int cvv, String expDate, Long userId) throws NotFoundException, InsufficientBalanceException;
        void paymentValidation(Long subscriptionId, String cardHolder, String cardNumber,int cvv, String expDate ) throws EntityInactiveException, NotFoundException, BadRequestException, InsufficientBalanceException;
}
