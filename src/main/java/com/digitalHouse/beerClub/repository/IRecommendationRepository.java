package com.digitalHouse.beerClub.repository;

import com.digitalHouse.beerClub.model.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IRecommendationRepository extends JpaRepository<Recommendation, Long> {

    @Query(value = "SELECT r.id, r.title, r.description, r.create_date, r.image_url, r.is_active, r.product_id  FROM recommendation r\n" +
            "JOIN recommendation_subscription rs on r.id = recommendation_id\n" +
            "JOIN subscriptions s on s.id = rs.subscription_id  \n" +
            "WHERE s.id = 1 AND MONTH (r.create_date) = 10 AND YEAR(r.create_date) = 2023 AND r.is_active = true\n" +
            "LIMIT 1;", nativeQuery = true)
        Recommendation findBySubscriptionIdAndCreateDate(@Param("id") Long id,@Param("month") int month ,@Param("year") int year);
}
