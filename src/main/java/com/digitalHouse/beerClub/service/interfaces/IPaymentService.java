package com.digitalHouse.beerClub.service.interfaces;

import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.model.dto.PaymentDTO;

public interface IPaymentService extends IService<PaymentDTO>{
    void saveTransaction(Long subscriptionId, String paymentMethod) throws NotFoundException;
}
