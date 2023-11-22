package com.digitalHouse.beerClub.model;

import com.digitalHouse.beerClub.model.dto.UserApplicationDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nombre no puede ser nulo")
    @Size(min=3)
    private String firstName;

    @NotBlank(message = "Apellido no puede ser nulo")
    @Size(min=2)
    private String lastName;

    @Column(unique = true)
    @NotBlank(message = "Correo no puede ser nulo")
    private String email;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate birthdate;

    @NotBlank(message = "Telefono no puede ser nulo")
    private String telephone;

    @Column(nullable = false, updatable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate subscriptionDate;

    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
        message = "La contraseña debe contener al menos 8 caracteres y cumplir las siguientes condiciones: al menos una mayúscula, una minúscula, un número, y un carácter especial (@#$%^&+=!)."
    )
    private String password;

    private boolean active;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Review> reviewList;

    private Long nextSubscriptionId;

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

    public User(UserApplicationDTO user) {
        this.firstName = user.getName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.birthdate = user.getBirthdate();
        this.telephone = user.getTelephone();
        role = RoleType.USER;
        active = true;
    }

    public void assignRole(RoleType roleType) {
        this.role = roleType;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }
}
