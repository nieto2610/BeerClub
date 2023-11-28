package com.digitalHouse.beerClub.mapper;

import com.digitalHouse.beerClub.model.dto.UserXPaymentAndSubcriptionFilterDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserXPaymentAndSubcriptionFilterDTORowMapper implements RowMapper<UserXPaymentAndSubcriptionFilterDTO> {

    @Override
    public UserXPaymentAndSubcriptionFilterDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserXPaymentAndSubcriptionFilterDTO dto = new UserXPaymentAndSubcriptionFilterDTO();
        dto.setName(rs.getString("name"));
        dto.setSubscription_date(rs.getTimestamp("subscription_date").toLocalDateTime());
        dto.setCountry(rs.getString("country"));
        dto.setStatus(rs.getString("status"));
        dto.setLastPaymentDate(rs.getTimestamp("lastPaymentDate").toLocalDateTime());
        dto.setLastPaidAmount(rs.getDouble("lastPaidAmount"));
        dto.setTotalAmount(rs.getDouble("totalAmount"));
        dto.setIs_active(rs.getInt("is_active"));
        return dto;
    }
}
