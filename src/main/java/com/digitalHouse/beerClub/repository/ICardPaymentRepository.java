package com.digitalHouse.beerClub.repository;

import com.digitalHouse.beerClub.model.CardPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.SplittableRandom;

@Repository
public interface ICardPaymentRepository extends JpaRepository<CardPayment, Long> {
   Optional<CardPayment> findByNumber(String number);
}
