package com.digitalHouse.beerClub.service.Implement;

import com.digitalHouse.beerClub.exeptions.BadRequestException;
import com.digitalHouse.beerClub.exeptions.NotFoundException;
import com.digitalHouse.beerClub.exeptions.ServiceException;
import com.digitalHouse.beerClub.model.Subscription;
import com.digitalHouse.beerClub.model.dto.SubscriptionDTO;
import com.digitalHouse.beerClub.repository.ISubscriptionRepository;
import com.digitalHouse.beerClub.service.interfaces.ISubscriptionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService implements ISubscriptionService {

    private final ISubscriptionRepository subscriptionRepository;

    public SubscriptionService(ISubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }


    @Override
    public List<SubscriptionDTO> searchAll() {
        return subscriptionRepository.findAll().stream().map(SubscriptionService::toDto).toList();
    }

    @Override
    public SubscriptionDTO searchById(Long id) throws NotFoundException {
        return subscriptionRepository.findById(id)
                .stream().map(SubscriptionService::toDto).findFirst()
                .orElseThrow(()-> new NotFoundException("Subscription not found"));
    }

    @Override
    public SubscriptionDTO create(SubscriptionDTO subscriptionDTO) throws BadRequestException {
            Subscription newSubscription = subscriptionRepository.save(toEntity(subscriptionDTO));
            return toDto(newSubscription);
    }

    @Override
    public SubscriptionDTO update(SubscriptionDTO subscriptionDTO, Long id) throws NotFoundException {
        return null;
    }

    @Override
    public void delete(Long id) throws ServiceException, NotFoundException {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(() -> new NotFoundException("Subscription not found"));
        subscription.setIsActive(false);
        subscriptionRepository.save(subscription);
    }

    private static SubscriptionDTO toDto (Subscription subscription) {
        ObjectMapper mapper = new ObjectMapper();
        SubscriptionDTO dto = mapper.convertValue(subscription,SubscriptionDTO.class);
        return  dto;
    }

    private static Subscription toEntity(SubscriptionDTO subscriptionDTO) {
        ObjectMapper mapper = new ObjectMapper();
        Subscription entity = mapper.convertValue(subscriptionDTO,Subscription.class);
        return entity;
    }
}
