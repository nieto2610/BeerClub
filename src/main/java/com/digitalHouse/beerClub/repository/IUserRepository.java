package com.digitalHouse.beerClub.repository;

import com.digitalHouse.beerClub.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    @Query("SELECT u FROM User u WHERE u.email = ?1")
    Optional<User> findByUserEmail(String userEmail);
    List<User> findByActiveTrue();
    @Query("SELECT u FROM User u WHERE u.nextSubscriptionId IS NOT NULL")
    List<User> findByUserNextSubscription();
    @Procedure(name = "filterByGlobalDataUser")
    List<User> filterByGlobalDataUser (Integer tipoSubscription, Integer esActivo, LocalDate fechaInicio, LocalDate fechaFin);
}
