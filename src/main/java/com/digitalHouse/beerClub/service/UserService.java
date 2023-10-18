package com.digitalHouse.beerClub.service;

import com.digitalHouse.beerClub.exceptions.*;
import com.digitalHouse.beerClub.model.User;
import com.digitalHouse.beerClub.model.dto.UserApplicationDTO;
import com.digitalHouse.beerClub.model.dto.UserDTO;

import java.util.List;

public interface UserService {

    UserDTO saveUser(UserApplicationDTO user) throws EmailExistsException, CompoundException;

    List<UserDTO> getUsers();

    public List<UserDTO> getAllActiveUsers();

    User findById(Long id) throws ResourceNotFoundException;

    UserDTO getUserById(Long id) throws ResourceNotFoundException;

    User findByEmail(String email);

    UserDTO getUserAuth(String email);

    UserDTO UpdateUser(UserApplicationDTO user) throws CompoundException;

    void UpdatePasswordUser(UserApplicationDTO user) throws MissingFieldsException, InvalidPasswordException;

    void activateUserSubscription(Long userId) throws ResourceNotFoundException;

    void softDeleteUser(Long id) throws ResourceNotFoundException;
}
