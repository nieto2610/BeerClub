package com.digitalHouse.beerClub.mapper;

import com.digitalHouse.beerClub.model.dto.PaymentFilterDTO;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentFilterDTORowMapper implements RowMapper<PaymentFilterDTO> {

    @Override
    public PaymentFilterDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        PaymentFilterDTO dto = new PaymentFilterDTO();
        dto.setAmount(rs.getDouble("amount"));
        dto.setCard_number(rs.getString("card_number"));
        dto.setDate(rs.getTimestamp("date").toLocalDateTime()); // Asegúrate de que la conversión aquí sea adecuada
        dto.setDescription(rs.getString("description"));
        dto.setInvoice_number(rs.getString("invoice_number"));
        dto.setEmail(rs.getString("email"));
        dto.setFirst_name(rs.getString("first_name"));
        dto.setLast_name(rs.getString("last_name"));
        return dto;
    }
}
