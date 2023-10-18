package com.digitalHouse.beerClub.model.dto;

import com.digitalHouse.beerClub.model.Address;
import com.digitalHouse.beerClub.model.RoleType;
import com.digitalHouse.beerClub.model.User;

import java.time.LocalDate;

public class UserDTO {
    private Long id;
    private String firstName, lastName, email;
    private LocalDate birthday;
    private String telephone;
    private LocalDate subscriptionDate;
    private Address address;
    private RoleType role;

    public UserDTO() {};

    public UserDTO(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.birthday = user.getBirthday();
        this.telephone = user.getTelephone();
        this.subscriptionDate = user.getSubscriptionDate();
        this.address = user.getAddress();
        this.role = user.getRole();
    }

    public Long getId() { return id; }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getBirthday() { return birthday; }

    public String getTelephone() {
        return telephone;
    }

    public LocalDate getSubscriptionDate() { return subscriptionDate; }

    public Address getAddress() { return address; }

    public RoleType getRole() { return role; }

    @Override
    public String toString() {
        return "UserDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", birthday='" + birthday + '\'' +
                ", telephone='" + telephone + '\'' +
                ", subscriptionDate=" + subscriptionDate +
                ", role=" + role +
                '}';
    }
}
