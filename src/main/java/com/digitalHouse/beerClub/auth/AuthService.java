package com.digitalHouse.beerClub.auth;

import com.digitalHouse.beerClub.exceptions.CustomUserAlreadyExistsException;
import com.digitalHouse.beerClub.jwt.JwtService;
import com.digitalHouse.beerClub.model.User;
import com.digitalHouse.beerClub.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final IUserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    public AuthResponse login(UserAuthRequest request){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails user = repository.findByUserEmail(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(user, request.getEmail());
        return AuthResponse.builder()
                .token(token)
                .build();
    }

    public AuthResponse register(User request) {
        Optional<User> existingUser = repository.findByUserEmail(request.getEmail());

        if (existingUser.isPresent()) {
            throw new CustomUserAlreadyExistsException("El email ya está registrado.");
        } else {
            User userClass = new User(
                    request.getFirstName(),
                    request.getLastName(),
                    request.getEmail(),
                    request.getBirthdate(),
                    request.getTelephone(),
                    request.getSubscriptionDate(),
                    passwordEncoder.encode(request.getPassword()),
                    request.getAddress()
            );
            repository.save(userClass);
            return AuthResponse.builder()
                    .token(jwtService.getToken(userClass, request.getEmail()))
                    .build();
        }
    }
}
