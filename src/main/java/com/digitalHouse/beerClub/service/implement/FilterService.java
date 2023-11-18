package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.mapper.PaymentFilterDTORowMapper;
import com.digitalHouse.beerClub.model.dto.PaymentFilterDTO;
import com.digitalHouse.beerClub.model.dto.UserDTO;
import com.digitalHouse.beerClub.repository.IPaymentRepository;
import com.digitalHouse.beerClub.repository.IUserRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilterService {
    private final JdbcTemplate jdbcTemplate;
    private final IUserRepository userRepository;
    private final Mapper mapper;

    public FilterService(JdbcTemplate jdbcTemplate, IUserRepository userRepository,Mapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Transactional
    public List<UserDTO> UserGlobalData(Integer typeSubscription, Integer isActive, LocalDate dateOne, LocalDate dateTwo) {
        return userRepository.filterByGlobalDataUser(typeSubscription, isActive, dateOne, dateTwo).stream().map(user -> mapper.converter(user, UserDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public List<PaymentFilterDTO> getFilteredPayments(String descripcion, Double amountMin, Double amountMax, LocalDate dateOne, LocalDate dateTwo) {
        String sql = "{call filterPaymentsData(?,?,?,?,?)}"; // La llamada al procedimiento almacenado

        return jdbcTemplate.query(
                sql,
                new Object[]{descripcion, amountMin, amountMax, dateOne, dateTwo}, // Parámetros
                new PaymentFilterDTORowMapper() // El RowMapper para mapear los resultados
        );
    }



}
