package com.digitalHouse.beerClub.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be null")
    @Size(min=3)
    private String firstName;

    @NotBlank(message = "LastName cannot be null")
    @Size(min=2)
    private String lastName;

    @Column(unique = true)
    @NotBlank(message = "Email cannot be null")
    @Email(message = "Email should be valid")
    private String email;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthdate;

    @NotBlank(message = "Telephone cannot be null")
    private String telephone;

    @Column(nullable = false, updatable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate subscriptionDate;

    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
        message = "The password must contain at least 8 characters and meet the following criteria: at least one uppercase letter, one lowercase letter, one number, and one special character @#$%^&+=!)."
    )
    private String password;

    private boolean active;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @JsonIgnore
    @OneToMany(mappedBy="user", fetch=FetchType.EAGER)
    private Set<CardPayment> cardPayments = new HashSet<>();

    public User(String firstName, String lastName, String email, LocalDate birthdate, String telephone, LocalDate subscriptionDate, String password, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthdate = birthdate;
        this.telephone = telephone;
        this.subscriptionDate = subscriptionDate;
        this.password = password;
        this.address = address;
        role = RoleType.USER;
        active = true;
    }

    public void addCard(CardPayment cardPayment) {
        cardPayments.add(cardPayment);
    }

    public void assignRole(RoleType roleType) {
        this.role = roleType;
    }

}
