package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.mapper.PaymentFilterDTORowMapper;
import com.digitalHouse.beerClub.mapper.UserXPaymentAndSubcriptionFilterDTORowMapper;
import com.digitalHouse.beerClub.model.dto.PaymentFilterDTO;
import com.digitalHouse.beerClub.model.dto.SubscriptionDTO;
import com.digitalHouse.beerClub.model.dto.UserDTO;
import com.digitalHouse.beerClub.model.dto.UserXPaymentAndSubcriptionFilterDTO;
import com.digitalHouse.beerClub.repository.ISubscriptionRepository;
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
    private final ISubscriptionRepository subRepository;
    private final Mapper mapper;

    public FilterService(JdbcTemplate jdbcTemplate, IUserRepository userRepository, ISubscriptionRepository subRepository, Mapper mapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.userRepository = userRepository;
        this.subRepository = subRepository;
        this.mapper = mapper;
    }

    @Transactional
    public List<UserXPaymentAndSubcriptionFilterDTO> getUserGlobalData(String typeSubscription, String paymentStatus,Integer isActive, LocalDate startDate, LocalDate endDate) {
        String sql = "{call filterByGlobalDataPayments(?,?,?,?,?)}"; // La llamada al procedimiento almacenado

        return jdbcTemplate.query(
                sql,
                new Object[]{typeSubscription, paymentStatus, isActive, startDate, endDate}, // Parámetros
                new UserXPaymentAndSubcriptionFilterDTORowMapper() // El RowMapper para mapear los resultados
        );
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

    @Transactional
    public List<SubscriptionDTO> getFilterSubscription(String description, Integer isRecommended , Integer isActive, Double amountMin, Double amountMax) {
        return subRepository.filterSubscriptionsData(description, isRecommended,isActive, amountMin, amountMax).stream().map(user -> mapper.converter(user, SubscriptionDTO.class)).collect(Collectors.toList());
    }


}
