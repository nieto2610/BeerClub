package com.digitalHouse.beerClub.repository;


import com.digitalHouse.beerClub.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReviewRepository extends JpaRepository<Review,Long> {
}
