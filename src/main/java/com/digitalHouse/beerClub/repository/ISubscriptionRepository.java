package com.digitalHouse.beerClub.repository;

import com.digitalHouse.beerClub.model.Subscription;
import com.digitalHouse.beerClub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ISubscriptionRepository extends JpaRepository<Subscription, Long> {
    @Procedure(name = "filterSubscriptionsData")
    List<Subscription> filterSubscriptionsData (String description, Integer isRecommended , Integer isActive, Double amountMin, Double amountMax);
}
