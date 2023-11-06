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
        PaymentDTO savePayment(PaymentApplicationDTO paymentApplicationDTO) throws NotFoundException, EntityInactiveException, BadRequestException, InsufficientBalanceException;

}
