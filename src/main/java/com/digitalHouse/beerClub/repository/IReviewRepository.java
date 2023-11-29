package com.digitalHouse.beerClub.repository;


import com.digitalHouse.beerClub.model.Payment;
import com.digitalHouse.beerClub.model.Review;
import com.digitalHouse.beerClub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IReviewRepository extends JpaRepository<Review,Long> {

    @Query(value="SELECT * FROM reviews WHERE user_id = ?1", nativeQuery = true)
    List<Review> findByUser (Long userId);
}
