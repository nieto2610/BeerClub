package com.digitalHouse.beerClub.service.interfaces;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.model.dto.CardAppDTO;
import com.digitalHouse.beerClub.model.dto.CardDTO;

public interface ICardService extends IService<CardDTO> {
    CardDTO createCard(CardAppDTO cardAppDTO) throws BadRequestException, NotFoundException;
    CardDTO activeCard(Long cardId) throws NotFoundException;
}
