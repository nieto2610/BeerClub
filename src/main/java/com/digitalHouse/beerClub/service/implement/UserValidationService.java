package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.*;
import com.digitalHouse.beerClub.model.User;
import com.digitalHouse.beerClub.model.dto.UserApplicationDTO;
import com.digitalHouse.beerClub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserValidationService {

    @Autowired
    private UserRepository userRepository;

    public void validateUserFields(UserApplicationDTO user) throws CompoundException {

        List<Exception> exceptions = new ArrayList<>();
        List<String> missingFields = new ArrayList<>();

        if (user.getFirstName().isBlank()) {
            missingFields.add("FirstName");
        }

        if (user.getLastName().isBlank()) {
            missingFields.add("LastName");
        }

        if (user.getEmail().isBlank()) {
            missingFields.add("Email");
        }

        if (user.getBirthday()== null) {
            missingFields.add("Birthday");
        }

        if (user.getTelephone().isBlank()) {
            missingFields.add("Telephone");
        }

        if (user.getCountry().isBlank()) {
            missingFields.add("Country");
        }

        if (user.getProvince().isBlank()) {
            missingFields.add("Province");
        }

        if (user.getCity().isBlank()) {
            missingFields.add("City");
        }

        if (user.getStreet().isBlank()) {
            missingFields.add("Street");
        }

        if (user.getNumber() == 0) {
            missingFields.add("Number");
        }

        if (user.getFloor() == 0) {
            missingFields.add("Floor");
        }

        if (user.getApartment().isBlank()) {
            missingFields.add("Apartment");
        }

        if (user.getZipCode().isBlank()) {
            missingFields.add("ZipCode");
        }

        if (user.getPassword().isBlank()) {
            missingFields.add("Password");
        } else if (!user.getPassword().matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$")) {
            exceptions.add(new InvalidPasswordException("The password must contain at least 8 characters and meet the following criteria: at least one uppercase letter, one lowercase letter, one digit, and one special character @#$%^&+=!)."));
        }

        if (!missingFields.isEmpty()) {
            String errorMessage = "The following fields are required: " + String.join(", ", missingFields);
            exceptions.add(new MissingFieldsException(errorMessage));
        }

        if (!exceptions.isEmpty()) {
            throw new CompoundException(exceptions);
        }
    }

    public void validateUser(UserApplicationDTO user) throws EmailExistsException {
        User existingUser = userRepository.findByEmail(user.getEmail());

        if(existingUser != null) {
            throw  new EmailExistsException("Email already exists");
        }
    }
}
