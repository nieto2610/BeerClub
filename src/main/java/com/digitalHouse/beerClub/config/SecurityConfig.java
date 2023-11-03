package com.digitalHouse.beerClub.config;

import com.digitalHouse.beerClub.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSecurity configuredHttp = http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                //Los endpoint Post que se pueden user sin autenticación
                                .requestMatchers(HttpMethod.POST,"/auth/**").permitAll()
                                //Los endpoint que se pueden ver sin autenticación
                                .requestMatchers(HttpMethod.GET,"/api/v1/swagger-ui/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/v3/api-docs/**").permitAll()
                                //Los endpoint que se pueden ver siendo USER
                                .requestMatchers(HttpMethod.GET,"/api/v1/users/email/**").hasRole("USER")
                                //Los endpoint que se pueden ver siendo ADMIN
                                .requestMatchers(HttpMethod.GET,"/api/v1/users/all").hasRole("ADMIN")
                                //Los endpoint que se pueden ver siendo ADMIN
                                .requestMatchers(HttpMethod.DELETE,"/api/v1/users/**").hasRole("ADMIN")
                                //Los endpoint que se pueden Modificar datos siendo USER
                                .requestMatchers(HttpMethod.GET,"/api/v1/users/update/passwword").hasRole("USER")
                                .anyRequest().authenticated()
                )
                .sessionManagement(sessionManager ->
                        sessionManager
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return configuredHttp.build();
    }
}
