package com.digitalHouse.beerClub.service.Implement;

import com.digitalHouse.beerClub.exeptions.BadRequestException;
import com.digitalHouse.beerClub.exeptions.NotFoundException;
import com.digitalHouse.beerClub.exeptions.ServiceException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.Benefit;
import com.digitalHouse.beerClub.model.Subscription;
import com.digitalHouse.beerClub.model.dto.BenefitDTO;
import com.digitalHouse.beerClub.model.dto.SubscriptionDTO;
import com.digitalHouse.beerClub.repository.IBenefitRepository;
import com.digitalHouse.beerClub.repository.ISubscriptionRepository;
import com.digitalHouse.beerClub.service.interfaces.ISubscriptionService;
import org.springframework.stereotype.Service;

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
        return subscriptionRepository.findAll().stream().filter(s -> s.getIsActive()).map(s -> mapper.converter(s,SubscriptionDTO.class)).toList();
    }

    @Override
    public SubscriptionDTO searchById(Long id) throws NotFoundException {
        return subscriptionRepository.findById(id)
                .stream().map(s -> mapper.converter(s,SubscriptionDTO.class)).findFirst()
                .orElseThrow(()-> new NotFoundException("Subscription not found"));
    }

    @Override
    public SubscriptionDTO create(SubscriptionDTO subscriptionDTO) throws BadRequestException {
            subscriptionDTO.setIsActive(true);
            Subscription newSubscription = subscriptionRepository.save(mapper.converter(subscriptionDTO,Subscription.class));
            return mapper.converter(newSubscription,SubscriptionDTO.class);
    }

    @Override
    public SubscriptionDTO update(SubscriptionDTO subscriptionDTO, Long id) throws NotFoundException {
//        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(() -> new NotFoundException("Subscription not found"));
//        subscription.setName(subscriptionDTO.getName());
//        subscription.setDescription(subscriptionDTO.getDescription());
//        subscription.setPrice(subscriptionDTO.getPrice());
//        subscription.setIsRecommended(subscriptionDTO.getIsRecommended());
//
//
//        subscription.setBenefits(subscriptionDTO.getBenefits().stream().map(b -> mapper.converter(b, Benefit.class)).toList());
//        Subscription subscriptionUpdated = subscriptionRepository.save(subscription);
//
//
//        return mapper.converter(subscriptionUpdated,SubscriptionDTO.class);
        return null;
    }

    @Override
    public void delete(Long id) throws ServiceException, NotFoundException {
        Subscription subscription = subscriptionRepository.findById(id).orElseThrow(() -> new NotFoundException("Subscription not found"));
        subscription.setIsActive(false);
        subscriptionRepository.save(subscription);
    }
}