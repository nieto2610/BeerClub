package com.digitalHouse.beerClub.model;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@ToString
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate birthday;
    private String telephone;
    private LocalDate subscriptionDate;
    private String password;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    public User(String firstName, String lastName, String email, LocalDate birthday,String telephone, LocalDate subscriptionDate, String password, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
        this.telephone = telephone;
        this.subscriptionDate = subscriptionDate;
        this.password = password;
        this.address = address;
        role = RoleType.USER;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public void assignRole(RoleType roleType) {
        this.role = roleType;
    }
}
