package com.digitalHouse.beerClub.service.interfaces;

import com.digitalHouse.beerClub.exceptions.*;
import com.digitalHouse.beerClub.model.User;
import com.digitalHouse.beerClub.model.dto.UserApplicationDTO;
import com.digitalHouse.beerClub.model.dto.UserAuthRequest;
import com.digitalHouse.beerClub.model.dto.UserDTO;
import com.digitalHouse.beerClub.exceptions.UserActiveException;
import com.digitalHouse.beerClub.model.dto.UserResponseDTO;

import java.util.List;

public interface IUserService extends IService<UserDTO>{

    UserResponseDTO saveUser(UserApplicationDTO user) throws NotFoundException, EntityInactiveException, InsufficientBalanceException, BadRequestException;

    List<UserDTO> getAllActiveUsers();

    User findById(Long id) throws NotFoundException;

    User findByEmail(String email);

    UserDTO getUserAuth(String email);

    UserDTO updateUser(UserApplicationDTO user, Long id) throws NotFoundException;

    void updatePasswordUser(UserAuthRequest user) throws NotFoundException;

    void activateUserSubscription(Long userId) throws NotFoundException, UserActiveException;
}
