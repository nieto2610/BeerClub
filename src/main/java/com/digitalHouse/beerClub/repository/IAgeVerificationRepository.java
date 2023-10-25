package com.digitalHouse.beerClub.repository;

import com.digitalHouse.beerClub.model.AgeVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAgeVerificationRepository extends JpaRepository<AgeVerification, Long> {
}
