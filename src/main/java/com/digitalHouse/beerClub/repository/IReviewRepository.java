package com.digitalHouse.beerClub.repository;


import com.digitalHouse.beerClub.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReviewRepository extends JpaRepository<Review,Long> {
    @Query(value = "SELECT * FROM REVIEW WHERE user_id = ?!", nativeQuery = true)
    List<Review> findByUserId (Long id);
}
