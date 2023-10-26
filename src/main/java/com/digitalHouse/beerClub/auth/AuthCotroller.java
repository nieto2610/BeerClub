package com.digitalHouse.beerClub.auth;

import com.digitalHouse.beerClub.model.dto.UserApplicationDTO;
import com.digitalHouse.beerClub.model.dto.UserDTO;
import com.digitalHouse.beerClub.service.interfaces.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthCotroller {
    @Autowired
    private IUserService IUserService;

    //INFO: este es el método de saveUser es de registro
    @Operation(summary="Add user", description="Add a new user", responses = {
            @ApiResponse(responseCode = "201",description = "CREATED",content = @Content(mediaType = "application/json",schema = @Schema(implementation = UserDTO.class)))})
    @PostMapping("/create")
    public ResponseEntity<Object> saveUser(@RequestBody UserApplicationDTO user) {
        UserDTO userDTO = IUserService.saveUser(user);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public String login() {
        return "Como vamos";
    }
}
