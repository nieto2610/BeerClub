package com.digitalHouse.beerClub.repository;

import com.digitalHouse.beerClub.model.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBenefitRepository extends JpaRepository<Benefit, Long> {
}
