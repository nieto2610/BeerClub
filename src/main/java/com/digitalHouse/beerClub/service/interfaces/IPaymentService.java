package com.digitalHouse.beerClub.service.interfaces;

import com.digitalHouse.beerClub.exceptions.*;
import com.digitalHouse.beerClub.model.CardPayment;
import com.digitalHouse.beerClub.model.Payment;
import com.digitalHouse.beerClub.model.PaymentStatus;
import com.digitalHouse.beerClub.model.User;
import com.digitalHouse.beerClub.model.dto.PaymentApplicationDTO;
import com.digitalHouse.beerClub.model.dto.PaymentDTO;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface IPaymentService {
        // Solo para Admin
        List<PaymentDTO> searchAll();
        // Solo para Admin
        List<PaymentDTO> findPaymentsByUserId(Long userId);
        // Para User autenticado
        PaymentDTO searchById(Long id, Authentication authentication) throws NotFoundException, ForbiddenException;
        // Para User autenticado
        List<PaymentDTO> getPaymentsUserAuth(Authentication authentication) throws NotFoundException, ForbiddenException;

        Payment savePayment(CardPayment card, User user) throws NotFoundException, InsufficientBalanceException;

        void paymentValidation(Long subscriptionId, String cardHolder, String cardNumber,String cvv, String expDate ) throws EntityInactiveException, NotFoundException, BadRequestException, InsufficientBalanceException;

        PaymentDTO createPaymentInvoice(Long userId) throws NotFoundException;

        PaymentDTO processPayment(PaymentApplicationDTO paymentDTO) throws NotFoundException, InsufficientBalanceException, EntityInactiveException, BadRequestException;

        PaymentDTO updatePaymentStatus(Long paymentId, PaymentStatus newStatus) throws NotFoundException;
}
