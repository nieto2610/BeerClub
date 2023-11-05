/*
package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.Subscription;
import com.digitalHouse.beerClub.model.Transaction;
import com.digitalHouse.beerClub.model.dto.TransactionDTO;
import com.digitalHouse.beerClub.repository.ISubscriptionRepository;
import com.digitalHouse.beerClub.repository.ITransactionRepository;
import com.digitalHouse.beerClub.service.interfaces.ITransactionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TransactionServiceImplement implements ITransactionService {

    private final ITransactionRepository transactionRepository;
    private final ISubscriptionRepository subscriptionRepository;
    private final Mapper transactionMapper;

    @Autowired
    public TransactionServiceImplement(ITransactionRepository transactionRepository, ISubscriptionRepository subscriptionRepository, Mapper mapper) {
        this.transactionRepository = transactionRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.transactionMapper = mapper;
    }

    @Override
    public List<TransactionDTO> searchAll() {
        return transactionRepository.findAll().stream().map(transaction -> transactionMapper.converter(transaction,TransactionDTO.class)).toList();
    }

    @Override
    public TransactionDTO searchById(Long id) throws NotFoundException {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
        return transactionMapper.converter(transaction, TransactionDTO.class);
    }

    @Override
    public TransactionDTO create(TransactionDTO entity) throws BadRequestException {
        return null;
    }

    @Override
    public TransactionDTO update(TransactionDTO entity, Long id) throws NotFoundException {
        return null;
    }

    @Override
    public void delete(Long id) throws ServiceException, NotFoundException {

    }

    @Transactional
    @Override
    public void saveTransaction(Long subscriptionId, String paymentMethod) throws NotFoundException {
        Subscription subscription = subscriptionRepository.findById(subscriptionId).orElseThrow(() -> new NotFoundException("Subscription not found"));

    }
}
*/
