package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.*;
import com.digitalHouse.beerClub.model.Address;
import com.digitalHouse.beerClub.model.User;
import com.digitalHouse.beerClub.model.dto.UserApplicationDTO;
import com.digitalHouse.beerClub.model.dto.UserDTO;
import com.digitalHouse.beerClub.repository.AddressRepository;
import com.digitalHouse.beerClub.repository.UserRepository;
import com.digitalHouse.beerClub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImplement implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserValidationService validationService;

    @Override
    public UserDTO saveUser(UserApplicationDTO user) throws EmailExistsException, CompoundException {
        System.out.println(user);
        if (user != null) {
            validationService.validateUserFields(user);
            validationService.validateUser(user);
        }
        Address address = new Address(user.getCountry(), user.getProvince(), user.getCity(), user.getStreet(), user.getNumber(), user.getFloor(), user.getApartment(), user.getZipCode());
        addressRepository.save(address);
        User newUser = new User(user.getFirstName(), user.getLastName(), user.getEmail(), user.getBirthday(), user.getTelephone(), LocalDate.now(), user.getPassword(), address);
        userRepository.save(newUser);
        return new UserDTO(newUser);
    }

    @Override
    public List<UserDTO> getUsers() {
        return userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllActiveUsers() {
        // Filtrar usuarios no eliminados
        List<UserDTO> users = userRepository.findByActiveTrue().stream().map(UserDTO::new).collect(Collectors.toList());
        System.out.println(users);
        return users;
    }

    @Override
    public User findById(Long id) throws ResourceNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    @Override
    public UserDTO getUserById(Long id) throws ResourceNotFoundException{
        return new UserDTO(this.findById(id));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDTO getUserAuth(String email) {
        return new UserDTO(this.findByEmail(email));
    }

    @Override
    public UserDTO UpdateUser(UserApplicationDTO user) throws CompoundException {
        validationService.validateUserFields(user);
        User searchedUser = userRepository.findByEmail(user.getEmail());
        searchedUser.setFirstName(user.getFirstName());
        searchedUser.setLastName(user.getLastName());
        searchedUser.setEmail(user.getEmail());
        searchedUser.setTelephone(user.getTelephone());
        userRepository.save(searchedUser);
        return new UserDTO(searchedUser);
    }

    @Override
    public void UpdatePasswordUser(UserApplicationDTO user) throws MissingFieldsException, InvalidPasswordException {
        if (user != null) {
            if (user.getEmail() == null || user.getEmail().isEmpty()) {
                throw new MissingFieldsException("The email is required.");
            }
            if (user.getPassword() == null || user.getPassword().isEmpty()) {
                throw new MissingFieldsException("The new password is required.");
            }

            // Validación de contraseña con expresión regular
            String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
            if (!user.getPassword().matches(passwordRegex)) {
                throw new InvalidPasswordException("The password must contain at least 8 characters and meet the following criteria: at least one uppercase letter, one lowercase letter, one digit, and one special character @#$%^&+=!).");
            }

            // Continuar con la actualización de la contraseña
            User searchedUser = userRepository.findByEmail(user.getEmail());
            if (searchedUser != null) {
                searchedUser.setPassword(user.getPassword());
                userRepository.save(searchedUser);
            }
        }
    }

    @Override
    public void activateUserSubscription(Long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public void softDeleteUser(Long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        if(user != null) {
            user.setActive(false);
            userRepository.save(user);
        }
    }


}
