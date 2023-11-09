package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.InsufficientBalanceException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.Account;
import com.digitalHouse.beerClub.model.Card;
import com.digitalHouse.beerClub.model.dto.CardAppDTO;
import com.digitalHouse.beerClub.model.dto.CardDTO;
import com.digitalHouse.beerClub.model.dto.CardResponseDTO;
import com.digitalHouse.beerClub.model.dto.CardType;
import com.digitalHouse.beerClub.repository.IAccountRepository;
import com.digitalHouse.beerClub.repository.ICardRepository;
import com.digitalHouse.beerClub.service.interfaces.ICardService;
import com.digitalHouse.beerClub.utils.CardUtils;
import jakarta.transaction.Transactional;
import jdk.jfr.StackTrace;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService implements ICardService {

    private final ICardRepository cardRepository;
    private final IAccountRepository accountRepository;
    private final Mapper mapper;

    public CardService(ICardRepository cardRepository, IAccountRepository accountRepository, Mapper mapper) {
        this.cardRepository = cardRepository;
        this.accountRepository = accountRepository;
        this.mapper = mapper;
    }


    @Override
    public List<CardDTO> searchAll() {
        return cardRepository.findAll()
                .stream().map(s -> mapper.converter(s, CardDTO.class)).collect(Collectors.toList());
    }

    @Override
    public CardDTO searchById(Long id) throws NotFoundException {
        return cardRepository.findById(id)
                .stream().map(s -> mapper.converter(s, CardDTO.class)).findFirst()
                .orElseThrow(() -> new NotFoundException("Card not found"));
    }

    @Override
    public CardDTO create(CardDTO entity) throws BadRequestException {
        return null;
    }

    @Override
    public CardDTO createCard(CardAppDTO cardAppDTO) throws BadRequestException, NotFoundException {

        String cardHolderName = cardAppDTO.getCardHolderName();
        String cardNumber = CardUtils.getCardNumber();
        int cvv = CardUtils.getCVV();
        LocalDate expirationDate = LocalDate.now().plusYears(5).with(TemporalAdjusters.lastDayOfMonth());
        CardType cardType = cardAppDTO.getCardType();

        if(cardAppDTO.getCardType().equals(CardType.DEBIT)){
            Long id = cardAppDTO.getAccountId();
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Account not found"));

            if(!account.getIsActive()){
                throw new NotFoundException("The Account is not active and cannot be modified.");
            }
            Card newCard = new Card(cardNumber, cardHolderName, expirationDate, cvv, account, cardType, true);
            Card card = cardRepository.save(newCard);
            return mapper.converter(card, CardDTO.class);
        }else{
            Double creditLimit = cardAppDTO.getCreditLimit();
            Card newCard = new Card(cardNumber, cardHolderName, expirationDate, cvv, cardType, creditLimit, true);
            Card card = cardRepository.save(newCard);
            return mapper.converter(card, CardDTO.class);
        }
    }

    @Transactional
    @Override
    public CardResponseDTO cardDebit(Long cardId, Double amount) throws NotFoundException, InsufficientBalanceException {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new NotFoundException("Card not found"));

        if(card.getCardType().equals(CardType.CREDIT)){
            if(card.getCreditLimit() < amount) {
                throw new InsufficientBalanceException("La tarjeta no tiene crédito suficiente");
            }

            Double balance = card.getCreditLimit() - amount;
            card.setCreditLimit(balance);
            cardRepository.save(card);

            CardResponseDTO cardResponseDTO = new CardResponseDTO();
            cardResponseDTO.setAmount(amount);
            cardResponseDTO.setDescription("Successful");

            return cardResponseDTO;
        }else {
            throw new NotFoundException("El tipo de tarjeta no es válido");
        }

    }

    @Override
    public CardDTO update(CardDTO cardDTO, Long id) throws NotFoundException {
        Card card = cardRepository.findById(id).orElseThrow(() -> new NotFoundException("Card not found"));
        if (!card.getIsActive()) {
            throw new NotFoundException("The Card is not active and cannot be modified.");
        }
        LocalDate expirationDate = cardDTO.getExpirationDate();
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Account not found"));
        if(!account.getIsActive()){
            throw new NotFoundException("The Account is not active and cannot be modified.");
        }

        card.setCardNumber(cardDTO.getCardNumber());
        card.setCardHolderName(cardDTO.getCardHolderName());
        card.setExpirationDate(expirationDate);
        card.setCvv(cardDTO.getCvv());
        card.setAccount(account);
        card.setIsActive(cardDTO.getIsActive());

        Card cardUpdated = cardRepository.save(card);

        return mapper.converter(cardUpdated, CardDTO.class);
    }

    @Override
    public void delete(Long id) throws ServiceException, NotFoundException {
        Card card = cardRepository.findById(id).orElseThrow(() -> new NotFoundException("Card not found"));
        if (!card.getIsActive()) {
            throw new NotFoundException("The Card is not active and cannot be deleted.");
        }
        card.setIsActive(false);
        cardRepository.save(card);
    }

    @Override
    public CardDTO activeCard(Long cardId) throws NotFoundException {
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new NotFoundException("Card not found"));
        if (card.getIsActive()) {
            throw new NotFoundException("The Card is active and cannot be actived.");
        }
        card.setIsActive(true);
        cardRepository.save(card);
        return mapper.converter(card, CardDTO.class);
    }

    @Override
    public CardDTO searchByCardNumber(String cardNumber) throws NotFoundException {
        Card card = cardRepository.findByCardNumber(cardNumber)
                .orElseThrow(() -> new NotFoundException("Card not found"));
        return mapper.converter(card, CardDTO.class);
    }
}
