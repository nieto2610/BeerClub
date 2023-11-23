package com.digitalHouse.beerClub.service.interfaces;

import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.model.dto.RecommendationDTO;

import java.time.LocalDate;

public interface IRecommendationService extends IService<RecommendationDTO>{

    RecommendationDTO searchBySubscriptionAndDate(Long subscriptionId, LocalDate date) throws NotFoundException;
}
