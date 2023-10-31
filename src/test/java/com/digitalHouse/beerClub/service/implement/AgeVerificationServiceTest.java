package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.AgeVerification;
import com.digitalHouse.beerClub.model.dto.AgeVerificationAppDTO;
import com.digitalHouse.beerClub.model.dto.AgeVerificationDTO;
import com.digitalHouse.beerClub.repository.IAgeVerificationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AgeVerificationServiceTest {

    @Mock
    private IAgeVerificationRepository ageVerificationRepository;
    @Mock
    private Mapper mapper;
    private AgeVerificationService ageVerificationService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        ageVerificationService = new AgeVerificationService(ageVerificationRepository, mapper);
    }

    @Test
    void searchAll() {
        LocalDate dateOfBirth1 = LocalDate.of(1990,1,1);
        int age1 = ageVerificationService.calculateAge(dateOfBirth1);
        AgeVerification ageVerification1= new AgeVerification("127.0.0.1", "Caba", dateOfBirth1,age1);

        LocalDate dateOfBirth2 = LocalDate.of(1987,7,20);
        int age2 = ageVerificationService.calculateAge(dateOfBirth2);
        AgeVerification ageVerification2 = new AgeVerification("127.0.0.2", "Bogotá", dateOfBirth2, age2);

        List<AgeVerification> ageVerifications = Arrays.asList(ageVerification1, ageVerification2);

        when(ageVerificationRepository.findAll()).thenReturn(ageVerifications);

        List<AgeVerificationDTO> result = ageVerificationService.searchAll();
        assertEquals(2, result.size());
    }

    @Test
    void searchById() throws NotFoundException {
        Long id = 1L;
        LocalDate dateOfBirth = LocalDate.of(1990,1,1);
        int age = ageVerificationService.calculateAge(dateOfBirth);
        AgeVerification ageVerification = new AgeVerification("127.0.0.1", "Caba", dateOfBirth, age);

        when(ageVerificationRepository.findById(id)).thenReturn(Optional.of(ageVerification));
        when(mapper.converter(ageVerification, AgeVerificationDTO.class)).thenReturn(new AgeVerificationDTO(ageVerification));

        AgeVerificationDTO result = ageVerificationService.searchById(id);
        assertEquals("127.0.0.1", result.getIp());
    }

    @Test
    void create() throws BadRequestException {
        AgeVerificationAppDTO ageVerificationAppDTO = new AgeVerificationAppDTO("127.0.0.1", "Bogotá", LocalDate.of(1990, 5,3));

        int age = ageVerificationService.calculateAge(ageVerificationAppDTO.getDateOfBirth());

        when(ageVerificationRepository.save(org.mockito.ArgumentMatchers.any(AgeVerification.class))).thenReturn(new AgeVerification(ageVerificationAppDTO.getIp(), ageVerificationAppDTO.getCity(), ageVerificationAppDTO.getDateOfBirth(), age));

        AgeVerificationDTO result = ageVerificationService.create(ageVerificationAppDTO);
        assertEquals("127.0.0.1", result.getIp());
    }
}