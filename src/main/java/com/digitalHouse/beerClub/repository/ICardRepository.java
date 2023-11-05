package com.digitalHouse.beerClub.repository;

import com.digitalHouse.beerClub.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICardRepository extends JpaRepository<Card, Long> {
}
