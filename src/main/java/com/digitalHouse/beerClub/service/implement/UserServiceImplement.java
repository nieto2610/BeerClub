package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.*;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.Address;
import com.digitalHouse.beerClub.model.CardPayment;
import com.digitalHouse.beerClub.model.Subscription;
import com.digitalHouse.beerClub.model.User;
import com.digitalHouse.beerClub.model.dto.*;
import com.digitalHouse.beerClub.repository.IAddressRepository;
import com.digitalHouse.beerClub.repository.ICardPaymentRepository;
import com.digitalHouse.beerClub.repository.ISubscriptionRepository;
import com.digitalHouse.beerClub.repository.IUserRepository;
import com.digitalHouse.beerClub.service.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImplement implements IUserService {

    private final IUserRepository userRepository;

    private final IAddressRepository addressRepository;

    private final ISubscriptionRepository subscriptionRepository;

    private final ICardPaymentRepository cardPaymentRepository;

    private final PaymentServiceImplement paymentServiceImplement;

    private final Mapper userMapper;

    @Autowired
    public UserServiceImplement(IUserRepository userRepository, IAddressRepository addressRepository, ISubscriptionRepository subscriptionRepository, ICardPaymentRepository cardPaymentRepository, PaymentServiceImplement paymentServiceImplement, Mapper userMapper) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.cardPaymentRepository = cardPaymentRepository;
        this.paymentServiceImplement = paymentServiceImplement;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> searchAll() {
        return userRepository.findAll().stream().map(user -> userMapper.converter(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public List<UserDTO> getAllActiveUsers() {
        // Filtrar usuarios activos
        List<UserDTO> users = userRepository.findByActiveTrue().stream().map(user -> userMapper.converter(user, UserDTO.class)).collect(Collectors.toList());
        return users;
    }

    @Override
    public User findById(Long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
    }

    @Override
    public UserDTO searchById(Long id) throws NotFoundException{
        UserDTO userDTO = userMapper.converter(this.findById(id), UserDTO.class);
        return userDTO;
    }

    @Override
    public UserDTO create(UserDTO entity) throws BadRequestException { return null; }

    @Override
    public UserDTO update(UserDTO entity, Long id) throws NotFoundException { return null; }

    @Override
    public UserResponseDTO saveUser(UserApplicationDTO user) throws NotFoundException, EntityInactiveException, InsufficientBalanceException, BadRequestException {
        int number = Integer.parseInt(user.getNumber());
        int floor = Integer.parseInt(user.getFloor());
        int cvv =  Integer.parseInt(user.getCvv());

        //verificación de pago
        paymentServiceImplement.paymentValidation(user.getSubscriptionId(), user.getCardHolder(), user.getCardNumber(), cvv, user.getExpDate() );

        Address address = new Address(user.getCountry(), user.getProvince(), user.getCity(), user.getStreet(), number, floor, user.getApartment(), user.getZipCode());
        addressRepository.save(address);

        CardPayment cardPayment = new CardPayment(user.getCardHolder(),user.getCardNumber(),cvv,user.getExpDate());
        cardPaymentRepository.save(cardPayment);

        LocalDate subscriptionDate = LocalDate.now();
        if (subscriptionDate.getDayOfMonth() >= 20) {
            // Si es el día 20 o posterior, establece la fecha de suscripción en el primer día del mes siguiente
            subscriptionDate = subscriptionDate.plusMonths(1).withDayOfMonth(1);
        }

        User newUser = new User(user.getName(), user.getLastName(), user.getEmail(), user.getBirthdate(), user.getTelephone(), subscriptionDate, user.getPassword(), address);
        Subscription subscription = subscriptionRepository.findById(user.getSubscriptionId()).orElseThrow(() -> new NotFoundException("Subscription not found."));
        newUser.setSubscription(subscription);
        newUser.addCard(cardPayment);
        User createdUser = userRepository.save(newUser);

        //guardar payment
        PaymentDTO paymentDTO = paymentServiceImplement.savePayment(user.getSubscriptionId(), user.getCardHolder(), user.getCardNumber(), cvv, user.getExpDate(),createdUser.getId());

        UserResponseDTO userResponseDTO = new UserResponseDTO();
        userResponseDTO.setAmount(paymentDTO.getAmount());
        userResponseDTO.setInvoiceDate(paymentDTO.getDate());
        userResponseDTO.setPlan(paymentDTO.getDescription());
        userResponseDTO.setInvoiceNumber(paymentDTO.getInvoiceNumber());

        return userResponseDTO;
    }

    @Override
    public UserDTO updateUser(UserApplicationDTO user, Long id) throws NotFoundException {
        User searchedUser = this.findById(id);
        if (!searchedUser.isActive()) {
            throw new NotFoundException("The user is not active and cannot be modified.");
        }
        searchedUser.setFirstName(user.getName());
        searchedUser.setLastName(user.getLastName());
        searchedUser.setEmail(user.getEmail());
        searchedUser.setTelephone(user.getTelephone());
        userRepository.save(searchedUser);
        UserDTO userDTO = userMapper.converter(searchedUser, UserDTO.class);
        return userDTO;
    }

    @Override
    public void delete(Long id) throws NotFoundException {
        User user = this.findById(id);
        if (!user.isActive()) {
            throw new NotFoundException("The user is not active and cannot be deleted.");
        }
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDTO getUserAuth(String email) {
        User user = this.findByEmail(email);
        System.out.println("USER:" + user);
        UserDTO userDTO = userMapper.converter(user, UserDTO.class);
        return userDTO;
    }

    @Override
    public void updatePasswordUser(UserAuthRequest user) throws NotFoundException {
        User searchedUser = userRepository.findByEmail(user.getEmail());
        if (!searchedUser.isActive()) {
            throw new NotFoundException("The user is not active.");
        }
        searchedUser.setPassword(user.getPassword());
        userRepository.save(searchedUser);
    }

    @Override
    public void activateUserSubscription(Long id) throws NotFoundException, UserActiveException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
        if (user.isActive()) {
            throw new UserActiveException("The user is active.");
        }
        user.setActive(true);
        userRepository.save(user);
    }

}
