package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.*;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.Address;
import com.digitalHouse.beerClub.model.Subscription;
import com.digitalHouse.beerClub.model.User;
import com.digitalHouse.beerClub.model.dto.UserApplicationDTO;
import com.digitalHouse.beerClub.model.dto.UserAuthRequest;
import com.digitalHouse.beerClub.model.dto.UserDTO;
import com.digitalHouse.beerClub.repository.IAddressRepository;
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

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IAddressRepository addressRepository;

    @Autowired
    private ISubscriptionRepository subscriptionRepository;

    private final Mapper userMapper;

    @Autowired
    public UserServiceImplement(IUserRepository userRepository, IAddressRepository addressRepository, ISubscriptionRepository subscriptionRepository,Mapper userMapper) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.subscriptionRepository = subscriptionRepository;
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
    public UserDTO saveUser(UserApplicationDTO user) throws NotFoundException {
        Address address = new Address(user.getCountry(), user.getProvince(), user.getCity(), user.getStreet(), user.getNumber(), user.getFloor(), user.getApartment(), user.getZipCode());
        addressRepository.save(address);
        LocalDate subscriptionDate = LocalDate.now();
        if (subscriptionDate.getDayOfMonth() >= 20) {
            // Si es el día 20 o posterior, establece la fecha de suscripción en el primer día del mes siguiente
            subscriptionDate = subscriptionDate.plusMonths(1).withDayOfMonth(1);
        }
        User newUser = new User(user.getName(), user.getLastName(), user.getEmail(), user.getBirthdate(), user.getTelephone(), subscriptionDate, user.getPassword(), address);
        Subscription subscription = subscriptionRepository.findById(user.getSubscriptionId()).orElseThrow(() -> new NotFoundException("Subscription not found."));
        newUser.setSubscription(subscription);
        userRepository.save(newUser);
        subscription.addUser(newUser);
        UserDTO userDTO = userMapper.converter(newUser, UserDTO.class);
        return userDTO;
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

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

}
