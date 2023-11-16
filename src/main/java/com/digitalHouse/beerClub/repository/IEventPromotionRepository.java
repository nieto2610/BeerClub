package com.digitalHouse.beerClub.repository;

import com.digitalHouse.beerClub.model.EventPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventPromotionRepository extends JpaRepository<EventPromotion, Long> {
}
