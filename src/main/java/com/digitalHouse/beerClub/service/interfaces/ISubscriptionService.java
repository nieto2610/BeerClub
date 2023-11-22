package com.digitalHouse.beerClub.service.interfaces;

import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.model.dto.SubscriptionDTO;

public interface ISubscriptionService extends IService<SubscriptionDTO> {
    SubscriptionDTO markAsRecommended(Long id) throws NotFoundException;
}
