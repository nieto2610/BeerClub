package com.digitalHouse.beerClub.repository;

import com.digitalHouse.beerClub.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISubscriptionRepository extends JpaRepository<Subscription, Long> {
}
