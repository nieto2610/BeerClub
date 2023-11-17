package com.digitalHouse.beerClub.config;

import com.digitalHouse.beerClub.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        //TODO: me falta subscription-controller
        //TODO: Realizar pruebas sobre si solo con el endpoint o con todo "user/id/{id}" o "user/id/**"
        HttpSecurity configuredHttp = http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authRequest ->
                        authRequest
                                //Los endpoint Post que se pueden user sin autenticación
                                .requestMatchers(HttpMethod.POST,"/auth/**", "/ageVerification/**").permitAll()
                                //Los endpoint Post que se pueden ADMIN con autenticación
                                .requestMatchers(HttpMethod.POST,"/users/**", "/faqs/**", "/users/create", "/subscriptions").permitAll()
                                //Los endpoint que se pueden ver sin autenticación
                                .requestMatchers(HttpMethod.GET,"/swagger-ui/**", "/v3/api-docs/**", "/subscriptions", "/faqs").permitAll()
                                //Los endpoint que se pueden ver siendo USER
                                .requestMatchers(HttpMethod.GET,"/users/email/**", "/address/**").hasAnyRole("USER", "ADMIN")
                                //Los endpoint que se pueden ver siendo ADMIN
                                .requestMatchers(HttpMethod.GET,"/users/all", "/users/active", "/users/id/**", "/subscriptions/**", "/faqs/**").hasRole("ADMIN")
                                //Los endpoint Put que se pueden user con autenticación
                                .requestMatchers(HttpMethod.PUT,"/address/update/**", "/users/update/**").hasRole("USER")
                                //Los endpoint Put que se pueden admin con autenticación
                                .requestMatchers(HttpMethod.PUT,"/faqs/**", "/users/activate/**", "/subscriptions/**").permitAll()
                                //Los endpoint que se pueden ver siendo ADMIN
                                .requestMatchers(HttpMethod.DELETE,"/users/**", "/faqs/**", "/subscriptions/**").hasRole("ADMIN")
                                //Los endpoint que se pueden Modificar datos siendo USER
                                .requestMatchers(HttpMethod.PATCH,"/users/update/passwword").hasAnyRole("USER", "ADMIN")
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

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Accept"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
