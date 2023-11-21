package com.digitalHouse.beerClub.service.implement;
/*
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.Address;
import com.digitalHouse.beerClub.model.Subscription;
import com.digitalHouse.beerClub.model.User;
<<<<<<< HEAD
import com.digitalHouse.beerClub.model.dto.*;
=======
import com.digitalHouse.beerClub.model.dto.UserApplicationDTO;
import com.digitalHouse.beerClub.auth.UserAuthRequest;
import com.digitalHouse.beerClub.model.dto.UserDTO;
>>>>>>> development
import com.digitalHouse.beerClub.repository.IAddressRepository;
import com.digitalHouse.beerClub.repository.ICardPaymentRepository;
import com.digitalHouse.beerClub.repository.ISubscriptionRepository;
import com.digitalHouse.beerClub.repository.IUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
class UserServiceImplementTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IAddressRepository addressRepository;

    @Mock
    private Mapper mapper;

    @InjectMocks
    private UserServiceImplement userServiceImplement;

    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @Mock
    private ICardPaymentRepository cardPaymentRepository;

    @BeforeEach
    void setUp() {
        mapper = new Mapper(new ModelMapper());
        userServiceImplement = new UserServiceImplement(userRepository,addressRepository,subscriptionRepository, cardPaymentRepository, mapper);
    }
*/
//@Test
//@DisplayName("Search all the users")
   /* void searchAll() {
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
        assertEquals(users.size(), result.size());
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
        assertEquals(activeUsers.size(), result.size());

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
        assertNotNull(result);
        assertEquals(userId, result.getId());
    }

    @Test
    @DisplayName("Save User")
    void saveUser() throws NotFoundException {
        // ARRANGE
        Subscription subscription = new Subscription(1L, "Novato", "Disfrutas de la cerveza y quieres conocer más acerca de ella", 200.0, List.of(), false, true);
<<<<<<< HEAD
        subscriptionRepository.save(subscription);
        CardPaymentDTO cardPayment = new CardPaymentDTO();
=======
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.of(subscription));

>>>>>>> development
        UserApplicationDTO userDto = new UserApplicationDTO("Juan", "Perez", "juan@beerClub.com",
                LocalDate.now().minusYears(23), "123456789", "Argentina", "Santa Fe", "Rosario", "Roca", 1200, 15, "A3", "3400","Juan123#",1L,cardPayment);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1L);
            return savedUser;
        });

        // ACT
        //UserResponseDTO result = userServiceImplement.saveUser(userDto);

        // ASSERT
<<<<<<< HEAD
        //assertNotNull(result);
       // assertNotNull(result.getId());
       // assertEquals(userDto.getName(), result.getFirstName());
=======
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(userDto.getName(), result.getFirstName());
        //assertTrue(subscription.getUsers().contains(result));
>>>>>>> development
    }

    @Test
    @DisplayName("Update User")
    void updateUser() throws NotFoundException {
        // ARRANGE
        Long userId = 1L;
        Address address = new Address("Argentina", "Santa Fe", "Rosario", "Roca", 1200, 15, "A3", "3400");
        User existingUser = new User("Juan", "Perez", "juan@beerClub.com", LocalDate.now().minusYears(23), "123456789", LocalDate.now().plusMonths(1).withDayOfMonth(1), "Juan123#", address);
        existingUser.setId(userId);
        CardPaymentDTO cardPayment = new CardPaymentDTO();

        // Configura el DTO con los datos de actualización
        UserApplicationDTO updatedUserData = new UserApplicationDTO("Juana", "Pérez", "juana@beerClub.com", LocalDate.now().minusYears(23), "987654321", "Argentina", "Santa Fe", "Rosario", "Roca", 1200, 15, "A3", "3400","Juana123#", 1L, cardPayment);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // ACT
        UserDTO result = userServiceImplement.updateUser(updatedUserData,userId);

        // ASSERT
        assertNotNull(result);
        assertEquals(userId, result.getId());
        assertEquals(updatedUserData.getEmail(), result.getEmail());
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
        assertDoesNotThrow(() -> userServiceImplement.delete(userId));

        // ASSERT
        assertFalse(existingUser.isActive());
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
        assertNotNull(result);
        assertEquals(userEmail, result.getEmail());
    }

    @Test
    @DisplayName("Update User Password")
    void updatePasswordUser() {
        // ARRANGE
        String userEmail = "juan@beerClub.com"; // Correo electrónico del usuario a actualizar
        String newPassword = "Juan456*%";

        Address address = new Address("Argentina", "Santa Fe", "Rosario", "Roca", 1200, 15, "A3", "3400");
        User existingUser = new User("Juan", "Perez", userEmail, LocalDate.now().minusYears(23), "123456789", LocalDate.now().plusMonths(1).withDayOfMonth(1), "Juan123#", address);
        when(userRepository.findByEmail(userEmail)).thenReturn(existingUser);

        // ACT
        assertDoesNotThrow(() -> userServiceImplement.updatePasswordUser(new UserAuthRequest(userEmail, newPassword)));

        // ASSERT
        assertEquals(newPassword, existingUser.getPassword());
    }

    @Test
    @DisplayName("Activate User Subscription")
    void activateUserSubscription() {
        // ARRANGE
        Long userId = 1L;
        Address address = new Address("Argentina", "Santa Fe", "Rosario", "Roca", 1200, 15, "A3", "3400");
        User existingUser = new User("Juan", "Perez", "juan@beerClub.com", LocalDate.now().minusYears(23), "123456789", LocalDate.now().plusMonths(1).withDayOfMonth(1), "Juan123#", address);
        existingUser.setActive(false);
        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));

        // ACT
        assertDoesNotThrow(() -> userServiceImplement.activateUserSubscription(userId));

        // ASSERT
        assertTrue(existingUser.isActive());
    }
}
*/


import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.dto.ProductDTO;
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
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
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


    @BeforeEach
    void setUp() {
        dataGenerator = new DataGenerator();
        mapper = new Mapper(new ModelMapper());
        userServiceImplement = new UserServiceImplement(userRepository, addressRepository, subscriptionRepository, paymentService, mapper);
    }

    @Test
    @DisplayName("✅ - Search Top 5 of products")
    void searchTop5() throws NotFoundException {

        //ARRAGE
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(dataGenerator.generateUser(1L)));

        //ACT
        List<ProductDTO> top5 = userServiceImplement.getTopFiveProducts(1L);

        //ASSERT
        Assertions.assertEquals(5, top5.size());
    }

    @Test
    @DisplayName("✅ - Search Top 5 of products - empty")
    void searchTop5Empty() throws NotFoundException {

        //ARRAGE
        when(userRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(dataGenerator.generateUserWithoutReviews(1L)));

        //ACT
        List<ProductDTO> top5 = userServiceImplement.getTopFiveProducts(1L);

        //ASSERT
        Assertions.assertTrue(top5.isEmpty());

    }


}