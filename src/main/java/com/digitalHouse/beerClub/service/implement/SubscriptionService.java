package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.Benefit;
import com.digitalHouse.beerClub.model.Subscription;
import com.digitalHouse.beerClub.model.dto.BenefitDTO;
import com.digitalHouse.beerClub.model.dto.SubscriptionDTO;
import com.digitalHouse.beerClub.repository.IBenefitRepository;
import com.digitalHouse.beerClub.repository.ISubscriptionRepository;
import com.digitalHouse.beerClub.service.interfaces.ISubscriptionService;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class SubscriptionService implements ISubscriptionService {

    private final ISubscriptionRepository subscriptionRepository;
    private final IBenefitRepository benefitRepository;
    private final Mapper mapper;

    public SubscriptionService(ISubscriptionRepository subscriptionRepository, IBenefitRepository benefitRepository, Mapper mapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.benefitRepository = benefitRepository;
        this.mapper = mapper;
    }

    @Override
    public List<SubscriptionDTO> searchAll() {
        return subscriptionRepository.findAll().stream().filter(s -> s.getIsActive()).map(s -> mapper.converter(s, SubscriptionDTO.class)).toList();
    }

    @Override
    public SubscriptionDTO searchById(Long id) throws NotFoundException {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(() -> new NotFoundException("Subscription not found"));
        return mapper.converter(subscription, SubscriptionDTO.class);
    }

    @Override
    public SubscriptionDTO create(SubscriptionDTO subscriptionDTO) throws BadRequestException {
        subscriptionDTO.setIsActive(true);
        Subscription newSubscription = subscriptionRepository.save(mapper.converter(subscriptionDTO, Subscription.class));
        return mapper.converter(newSubscription, SubscriptionDTO.class);
    }

    @Override
    public SubscriptionDTO update(SubscriptionDTO subscriptionDTO, Long id) throws NotFoundException {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(() -> new NotFoundException("Subscription not found"));
        subscription.setName(subscriptionDTO.getName());
        subscription.setDescription(subscriptionDTO.getDescription());
        subscription.setPrice(subscriptionDTO.getPrice());
        subscription.setIsRecommended(subscriptionDTO.getIsRecommended());

        // set new benefits
        List<BenefitDTO> newBenefits = new ArrayList<>(subscriptionDTO.getBenefits());

        subscription.getBenefits().clear();
        subscription.getBenefits().addAll(newBenefits.stream().map(b -> mapper.converter(b, Benefit.class)).toList());

        Subscription subscriptionUpdated = subscriptionRepository.save(subscription);
        return mapper.converter(subscriptionUpdated, SubscriptionDTO.class);
    }

    @Override
    public void delete(Long id) throws ServiceException, NotFoundException {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(() -> new NotFoundException("Subscription not found"));
        subscription.setIsActive(false);
        subscription.setIsRecommended(false);
        subscriptionRepository.save(subscription);
    }

    @Override
    public SubscriptionDTO markAsRecommended(Long id) throws NotFoundException {
        Subscription findedSubscription = subscriptionRepository.findById(id).filter(s -> s.getIsActive()).orElseThrow(() -> new NotFoundException("Subscripción no encontrada"));
        findedSubscription.setIsRecommended(true);
        subscriptionRepository.save(findedSubscription);
        subscriptionRepository.findAll().stream().filter(subscription -> !subscription.getId().equals(id))
                .forEach(subscription -> {
                    subscription.setIsRecommended(false);
                    subscriptionRepository.save(subscription);
                });
        return mapper.converter(findedSubscription, SubscriptionDTO.class);
    }
}
