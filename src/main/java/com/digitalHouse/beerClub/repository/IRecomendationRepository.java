package com.digitalHouse.beerClub.repository;

import com.digitalHouse.beerClub.model.Recomendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRecomendationRepository extends JpaRepository<Recomendation, Integer> {
}
