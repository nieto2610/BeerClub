package com.digitalHouse.beerClub.service.interfaces;

import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.model.dto.CardPaymentDTO;

public interface ICardPaymentService extends IService<CardPaymentDTO>{
    CardPaymentDTO searchByNumber(String number) throws NotFoundException;
    CardPaymentDTO updateByNumber(CardPaymentDTO cardPaymentDTO, String number) throws NotFoundException;
}
