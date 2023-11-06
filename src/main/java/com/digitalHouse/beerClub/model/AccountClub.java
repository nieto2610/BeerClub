package com.digitalHouse.beerClub.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "accountClub")
public class AccountClub {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private String accountHolder;
    private LocalDate creationDate;
    private Double balance;
    private Boolean isActive;

    @OneToOne
    @JoinColumn(name = "card_id")
    private Card card;

    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    private Set<Payment> payments = new HashSet<>();

    public void addTransaction(Payment payment) {
        payment.setAccount(this);
        payments.add(payment);
    }

}
