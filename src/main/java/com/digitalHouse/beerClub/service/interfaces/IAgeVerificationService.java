package com.digitalHouse.beerClub.service.interfaces;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.model.dto.AgeVerificationAppDTO;
import com.digitalHouse.beerClub.model.dto.AgeVerificationDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IAgeVerificationService {
    List<AgeVerificationDTO> searchAll() throws NotFoundException;
    AgeVerificationDTO searchById(Long id) throws NotFoundException;
    AgeVerificationDTO create(AgeVerificationAppDTO entity) throws BadRequestException;
}
