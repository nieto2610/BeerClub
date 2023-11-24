package com.digitalHouse.beerClub.repository;

import com.digitalHouse.beerClub.model.EventPromotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IEventPromotionRepository extends JpaRepository<EventPromotion, Long> {
    @Query("SELECT e FROM EventPromotion e " +
            "WHERE (:startDateTime IS NULL OR e.dateTime BETWEEN :startDateTime AND :endDateTime) " +
            "AND (:startValidUntil IS NULL OR e.validUntil BETWEEN :startValidUntil AND :endValidUntil) " +
            "AND (:location IS NULL OR UPPER(e.location) LIKE UPPER(CONCAT('%', :location, '%')))")
    List<EventPromotion> findByFilters(
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime,
            @Param("startValidUntil") LocalDate startValidUntil,
            @Param("endValidUntil") LocalDate endValidUntil,
            @Param("location") String location
    );


}
