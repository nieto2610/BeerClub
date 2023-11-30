package com.digitalHouse.beerClub.service.interfaces;

import com.digitalHouse.beerClub.exceptions.*;
import com.digitalHouse.beerClub.model.Payment;
import com.digitalHouse.beerClub.model.User;
import com.digitalHouse.beerClub.model.dto.UserAdminDTO;
import com.digitalHouse.beerClub.model.dto.ProductDTO;
import com.digitalHouse.beerClub.model.dto.UserApplicationDTO;
import com.digitalHouse.beerClub.auth.UserAuthRequest;
import com.digitalHouse.beerClub.model.dto.UserDTO;
import com.digitalHouse.beerClub.exceptions.UserActiveException;
import org.springframework.security.core.Authentication;
import com.digitalHouse.beerClub.model.dto.UserSubscriptionDTO;

import java.util.List;

public interface IUserService extends IService<UserDTO>{

    Payment saveUser(UserApplicationDTO user) throws NotFoundException, EntityInactiveException, InsufficientBalanceException, BadRequestException;

    void saveAdmin(UserAdminDTO adminDTO);

    List<UserDTO> searchAll();

    List<UserDTO> getAllActiveUsers();

    User findById(Long id) throws NotFoundException;

    UserDTO searchById(Long id) throws NotFoundException;

    User findByEmail(String email);

    UserDTO getUserAuth(String email);

    UserDTO updateUser(UserApplicationDTO user, Long id, Authentication authentication) throws NotFoundException, ForbiddenException;

    void updatePasswordUser(UserAuthRequest user, Authentication authentication) throws NotFoundException, ForbiddenException;

    void delete(Long id) throws NotFoundException;

    void activateUser(Long userId) throws NotFoundException, UserActiveException;


    UserDTO updateUserSubscription(UserSubscriptionDTO userSubscriptionDTO) throws NotFoundException;
}

