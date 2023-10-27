package com.digitalHouse.beerClub.repository;

import com.digitalHouse.beerClub.model.Faqs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFaqsRepository extends JpaRepository<Faqs,Long> {

}