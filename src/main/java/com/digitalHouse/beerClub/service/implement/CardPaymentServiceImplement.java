package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.CardPayment;
import com.digitalHouse.beerClub.model.dto.CardPaymentDTO;
import com.digitalHouse.beerClub.repository.ICardPaymentRepository;
import com.digitalHouse.beerClub.service.interfaces.ICardPaymentService;
import com.digitalHouse.beerClub.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardPaymentServiceImplement implements ICardPaymentService {

    @Autowired
    private ICardPaymentRepository cardRepository;

    @Autowired
    private IUserService userService;

    private final Mapper cardMapper;

    @Autowired
    public CardPaymentServiceImplement(ICardPaymentRepository cardRepository, IUserService userService, Mapper cardMapper) {
        this.cardRepository = cardRepository;
        this.userService = userService;
        this.cardMapper = cardMapper;
    }

    @Override
    public List<CardPaymentDTO> searchAll() {
        return cardRepository.findAll().stream().map(card -> cardMapper.converter(card, CardPaymentDTO.class)).toList();
    }

    @Override
    public CardPaymentDTO searchById(Long id) throws NotFoundException {
        return null;
    }

    @Override
    public CardPaymentDTO create(CardPaymentDTO entity) throws BadRequestException {
      return null;
    }

    @Override
    public CardPaymentDTO update(CardPaymentDTO entity, Long id) throws NotFoundException {
        return null;
    }

    @Override
    public void delete(Long id) throws ServiceException, NotFoundException {

    }

    @Override
    public CardPaymentDTO searchByNumber(String number) throws NotFoundException {
        CardPayment cardPayment = cardRepository.findByNumber(number).orElseThrow(() -> new NotFoundException("Card not found with number: " + number));
        return cardMapper.converter(cardPayment, CardPaymentDTO.class);
    }

    @Override
    public CardPaymentDTO updateByNumber(CardPaymentDTO cardPaymentDTO, String number) throws NotFoundException {
        CardPaymentDTO cardPayment = this.searchByNumber(number);
        cardPayment.setCardHolder(cardPaymentDTO.getCardHolder());
        cardPayment.setNumber(cardPaymentDTO.getNumber());
        cardPayment.setCvv(cardPaymentDTO.getCvv());
        cardPayment.setExpDate(cardPaymentDTO.getExpDate());
        return cardPayment;
    }
}
