package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.*;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.*;
import com.digitalHouse.beerClub.model.dto.UserAdminDTO;
import com.digitalHouse.beerClub.model.dto.UserApplicationDTO;
import com.digitalHouse.beerClub.auth.UserAuthRequest;
import com.digitalHouse.beerClub.model.dto.UserDTO;
import com.digitalHouse.beerClub.model.dto.UserSubscriptionDTO;
import com.digitalHouse.beerClub.repository.IAddressRepository;
import com.digitalHouse.beerClub.repository.ISubscriptionRepository;
import com.digitalHouse.beerClub.repository.IUserRepository;
import com.digitalHouse.beerClub.service.interfaces.IUserService;
import com.digitalHouse.beerClub.utils.TransformationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplement implements IUserService {

    private final IUserRepository userRepository;

    private final IAddressRepository addressRepository;

    private final ISubscriptionRepository subscriptionRepository;

    private final PaymentServiceImplement paymentServiceImplement;

    @Autowired
    private final EmailService emailService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    private final Mapper userMapper;

    @Autowired
    public UserServiceImplement(IUserRepository userRepository, IAddressRepository addressRepository, ISubscriptionRepository subscriptionRepository, PaymentServiceImplement paymentServiceImplement, EmailService emailService, PasswordEncoder passwordEncoder, Mapper userMapper) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.paymentServiceImplement = paymentServiceImplement;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> searchAll() {
        return userRepository.findAll().stream().map(user -> userMapper.converter(user, UserDTO.class)).toList();
    }

    @Override
    public List<UserDTO> getAllActiveUsers() {
        // Filtrar usuarios activos
        List<UserDTO> users = userRepository.findByActiveTrue().stream().map(user -> userMapper.converter(user, UserDTO.class)).toList();
        return users;
    }


    @Override
    public User findById(Long id) throws NotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("No se encontró el usuario con ID: " + id));
    }

    @Override
    public UserDTO searchById(Long id) throws NotFoundException {
        User user = this.findById(id);
        UserDTO userDTO = userMapper.converter(user, UserDTO.class);
        return userDTO;
    }

    @Override
    public UserDTO create(UserDTO entity) throws BadRequestException {
        return null;
    }

    @Override
    public UserDTO update(UserDTO user, Long id) throws NotFoundException {
        User searchedUser = this.findById(id);
        if (!searchedUser.isActive()) {
            throw new NotFoundException("The user is not active and cannot be modified.");
        }
        searchedUser.setFirstName(user.getFirstName());
        searchedUser.setLastName(user.getLastName());
        searchedUser.setEmail(user.getEmail());
        searchedUser.setTelephone(user.getTelephone());
        userRepository.save(searchedUser);
        addressRepository.save(user.getAddress());
        UserDTO userDTO = userMapper.converter(searchedUser, UserDTO.class);
        return userDTO;
    }

    @Override
    public Payment saveUser(UserApplicationDTO user) throws NotFoundException, InsufficientBalanceException, CustomUserAlreadyExistsException {

        emailValidation(user.getEmail());

        Optional<User> existingUser = userRepository.findByUserEmail(user.getEmail());

        if (existingUser.isPresent()) {
            throw new CustomUserAlreadyExistsException("El email ya está registrado.");
        }

        Address address = new Address(user.getCountry(), user.getProvince(), user.getCity(),
                user.getStreet(), TransformationUtils.getNumber(user.getNumber()),
                TransformationUtils.getNumber(user.getFloor()), user.getApartment(), user.getZipCode());
        addressRepository.save(address);

        Subscription subscription = subscriptionRepository.findById(user.getSubscriptionId()).orElseThrow(() -> new NotFoundException("Subscription not found."));
        CardPayment cardPayment = new CardPayment(user.getCardHolder(), user.getCardNumber(), TransformationUtils.getNumber(user.getCvv()), user.getExpDate());

        User newUser = new User(user);
        newUser.setSubscriptionDate(getSubscriptionDate());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setAddress(address);
        newUser.setSubscription(subscription);
        User createdUser = userRepository.save(newUser);

        Payment payment = paymentServiceImplement.savePayment(cardPayment, createdUser);

        //Envio Email
        String to = user.getEmail();
        String subject = "¡Bienvenido a Beer Club";
        String username = user.getName() + " " + user.getLastName();
        String invoice = payment.getInvoiceNumber();
        String amount = String.valueOf(payment.getAmount());
        String description = payment.getDescription();
        String state = String.valueOf(PaymentStatus.APROBADO);

        String content = emailService.buildContentWellcomeEmail(username, invoice, amount, description, state);
        emailService.sendHtmlMessage(to, subject, content);
        return payment;
    }

    @Override
    public void saveAdmin(UserAdminDTO adminDTO) {
        User adminUser = new User(adminDTO);
        adminUser.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        userRepository.save(adminUser);
    }

    @Override
    public UserDTO updateUser(UserApplicationDTO user, Long id, Authentication authentication) throws NotFoundException, ForbiddenException {
        User searchedUser = this.findById(id);
        if (!authentication.getName().equals(searchedUser.getEmail())) {
            throw new ForbiddenException("No tienes permisos para actualizar este usuario");
        }
        if (!searchedUser.isActive()) {
            throw new NotFoundException("El usuario no está activo y no se puede modificar");
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
            throw new NotFoundException("El usuario no está activo y no se puede eliminar");
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
        UserDTO userDTO = userMapper.converter(user, UserDTO.class);
        return userDTO;
    }

    @Override
    public void updatePasswordUser(UserAuthRequest user, Authentication authentication) throws NotFoundException, ForbiddenException {
        User currentUser = userRepository.findByEmail(authentication.getName());

        if (!user.getEmail().equals(currentUser.getEmail())) {
            throw new ForbiddenException("El email proporcionado no coincide con el del usuario autenticado");
        }

        if (!currentUser.isActive()) {
            throw new NotFoundException("El usuario no está activo");
        }
        currentUser.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(currentUser);
    }

    @Override
    public void activateUser(Long id) throws NotFoundException, UserActiveException {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found with ID: " + id));
        if (user.isActive()) {
            throw new UserActiveException("El usuario está activo");
        }
        user.setActive(true);
        userRepository.save(user);
    }

    private LocalDate getSubscriptionDate() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.getDayOfMonth() >= 20 ? currentDate.plusMonths(1).withDayOfMonth(1) : currentDate;
    }

    private void emailValidation(String email) {
        User existingUser = userRepository.findByEmail(email);

        if (existingUser != null) {
            throw new CustomUserAlreadyExistsException("El correo electrónico '" + email + "' ya está registrado. Por favor, ingresa otro.");
        }
    }

    @Override
    public UserDTO updateUserSubscription(UserSubscriptionDTO userSubscriptionDTO) throws NotFoundException {
        User user = userRepository.findById(userSubscriptionDTO.getUserId()).orElseThrow(() -> new NotFoundException("User not found with ID: " + userSubscriptionDTO.getUserId()));
        if (!user.isActive()) {
            throw new NotFoundException("User not found with ID: " + userSubscriptionDTO.getUserId());
        }

        Subscription newSubscription = subscriptionRepository.findById(userSubscriptionDTO.getNewSubscriptionId()).orElseThrow(() -> new NotFoundException("Subscription not found"));
        if (!newSubscription.getIsActive()) {
            throw new NotFoundException("Subscription not found");
        }

        user.setNextSubscriptionId(userSubscriptionDTO.getNewSubscriptionId());
        userRepository.save(user);

        return userMapper.converter(user, UserDTO.class);
    }

    public void updateUserSubscriptionWorker() throws NotFoundException {
        List<User> userList = userRepository.findByUserNextSubscription();

        if (!userList.isEmpty()) {

            for (User user : userList) {
                Subscription subscription = subscriptionRepository.findById(user.getNextSubscriptionId()).orElseThrow(() -> new NotFoundException("Subscription not found"));
                user.setSubscription(subscription);
                user.setNextSubscriptionId(null);
                userRepository.save(user);
            }
        }
    }
}
