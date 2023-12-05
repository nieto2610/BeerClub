package com.digitalHouse.beerClub.repository;

import com.digitalHouse.beerClub.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Month;
import java.time.Year;
import java.util.List;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT p FROM Payment p WHERE p.userId = :userId")
    List<Payment> findByUserId(@Param("userId") Long userId);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END " +
            "FROM Payment p " +
            "WHERE p.userId = :userId " +
            "AND p.invoiceDate IS NOT NULL " +
            "AND YEAR(p.invoiceDate) = :year " +
            "AND MONTH(p.invoiceDate) = :month")
    boolean existsInvoiceByUserId(
            @Param("userId") Long userId,
            @Param("year") int year,
            @Param("month") int month
    );

    @Query("SELECT p FROM Payment p " +
            "WHERE p.userId = :userId " +
            "AND p.date IS NOT NULL " +
            "AND YEAR(p.date) = :year " +
            "AND MONTH(p.date) = :month")
    Payment findPaymentByUserIdAndMonthYear(
            @Param("userId") Long userId,
            @Param("year") int year,
            @Param("month") int month
    );

}
