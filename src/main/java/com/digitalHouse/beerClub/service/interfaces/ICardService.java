package com.digitalHouse.beerClub.service.interfaces;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.InsufficientBalanceException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.model.dto.CardAppDTO;
import com.digitalHouse.beerClub.model.dto.CardDTO;
import com.digitalHouse.beerClub.model.dto.CardResponseDTO;

public interface ICardService extends IService<CardDTO> {
    CardDTO createCard(CardAppDTO cardAppDTO) throws BadRequestException, NotFoundException;
    CardResponseDTO cardDebit(Long cardId, Double amount) throws NotFoundException, InsufficientBalanceException;
    CardResponseDTO cardCredit(Long cardId, Double amount) throws NotFoundException;
    CardDTO activeCard(Long cardId) throws NotFoundException;
    CardDTO searchByCardNumber(String cardNumber) throws NotFoundException;
}
