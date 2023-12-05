package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.*;
import com.digitalHouse.beerClub.model.dto.UserDTO;
import com.digitalHouse.beerClub.repository.IAddressRepository;
import com.digitalHouse.beerClub.repository.ISubscriptionRepository;
import com.digitalHouse.beerClub.repository.IUserRepository;
import com.digitalHouse.beerClub.service.implement.utils.DataGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplementTest {

    DataGenerator dataGenerator;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IAddressRepository addressRepository;

    @Mock
    PaymentServiceImplement paymentService;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private UserServiceImplement userServiceImplement;

    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private EmailService emailService;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dataGenerator = new DataGenerator();
        mapper = new Mapper(new ModelMapper());
        userServiceImplement = new UserServiceImplement(userRepository, addressRepository, subscriptionRepository, paymentService, emailService, passwordEncoder, mapper);
    }

    @Test
    @DisplayName("Search all the users")
    void searchAll() {
        //ARRANGE
        Address address = new Address("Argentina", "Santa Fe", "Rosario", "Roca", 1200, 15, "A3","3400");
        List<User> users = new ArrayList<>();
        users.add(new User("Juan","Perez", "juan@beerClub.com", LocalDate.now().minusYears(23), "123456789", LocalDate.now().plusMonths(1).withDayOfMonth(1),"Juan123#", address));
        users.add(new User("Juana","Perez", "juana@beerClub.com", LocalDate.now().minusYears(25), "123456789", LocalDate.now().plusMonths(1).withDayOfMonth(1),"Juana123#", address));
        User user = new User("Ana","Perez", "ana@beerClub.com", LocalDate.now().minusYears(30), "123456789", LocalDate.now().plusMonths(1).withDayOfMonth(1),"Ana123#*", address);
        user.setActive(false);
        users.add(user);
        when(userRepository.findAll()).thenReturn(users);

        //ACT
        List<UserDTO> result = userServiceImplement.searchAll();

        //ASSERT
        Assertions.assertEquals(users.size(), result.size());
    }

    @Test
    @DisplayName("Search all the active users")
    void getAllActiveUsers() {
        //ARRANGE
        Address address = new Address("Argentina", "Santa Fe", "Rosario", "Roca", 1200, 15, "A3","3400");
        List<User> activeUsers = new ArrayList<>();
        activeUsers.add(new User("Juan","Perez", "juan@beerClub.com", LocalDate.now().minusYears(23), "123456789", LocalDate.now().plusMonths(1).withDayOfMonth(1),"Juan123#", address));
        activeUsers.add(new User("Juana","Perez", "juana@beerClub.com", LocalDate.now().minusYears(25), "123456789", LocalDate.now().plusMonths(1).withDayOfMonth(1),"Juana123#", address));
        when(userRepository.findByActiveTrue()).thenReturn(activeUsers);

        //ACT
        List<UserDTO> result = userServiceImplement.getAllActiveUsers();

        //ASSERT
        Assertions.assertEquals(activeUsers.size(), result.size());

    }

    @Test
    @DisplayName("Find User by ID")
    void findById() throws NotFoundException {
        // ARRANGE
        Long userId = 1L;
        Address address = new Address("Argentina", "Santa Fe", "Rosario", "Roca", 1200, 15, "A3", "3400");
        User user = new User("Juan", "Perez", "juan@beerClub.com", LocalDate.now().minusYears(23), "123456789", LocalDate.now().plusMonths(1).withDayOfMonth(1), "Juan123#", address);
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // ACT
        UserDTO result = userServiceImplement.searchById(userId);

        // ASSERT
        Assertions.assertNotNull(result);
        Assertions.assertEquals(userId, result.getId());
    }


    @Test
    @DisplayName("Find User by Email")
    void findByEmail() {
        // ARRANGE
        String userEmail = "juan@beerClub.com";
        Address address = new Address("Argentina", "Santa Fe", "Rosario", "Roca", 1200, 15, "A3", "3400");
        User existingUser = new User("Juan", "Perez", userEmail, LocalDate.now().minusYears(23), "123456789", LocalDate.now().plusMonths(1).withDayOfMonth(1), "Juan123#", address);
        when(userRepository.findByEmail(userEmail)).thenReturn(existingUser);

        // ACT
        User result = userServiceImplement.findByEmail(userEmail);

        // ASSERT
        Assertions.assertNotNull(result);
        Assertions.assertNotNull(userEmail, result.getEmail());
    }

    @Test
    @DisplayName("Delete User")
    void delete() {
        // ARRANGE
        Long userId = 1L;
        Address address = new Address("Argentina", "Santa Fe", "Rosario", "Roca", 1200, 15, "A3", "3400");
        User existingUser = new User("Juan", "Perez", "juan@beerClub.com", LocalDate.now().minusYears(23), "123456789", LocalDate.now().plusMonths(1).withDayOfMonth(1), "Juan123#", address);
        existingUser.setId(userId);

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        // ACT
        Assertions.assertDoesNotThrow(() -> userServiceImplement.delete(userId));

        // ASSERT
        Assertions.assertFalse(existingUser.isActive());
    }

}