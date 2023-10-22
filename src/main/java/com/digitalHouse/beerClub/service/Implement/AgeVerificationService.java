package com.digitalHouse.beerClub.service.Implement;

import com.digitalHouse.beerClub.exeptions.BadRequestException;
import com.digitalHouse.beerClub.exeptions.NotFoundException;
import com.digitalHouse.beerClub.model.AgeVerification;
import com.digitalHouse.beerClub.model.dto.AgeVerificationAppDTO;
import com.digitalHouse.beerClub.model.dto.AgeVerificationDTO;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.repository.IAgeVerificationRepository;
import com.digitalHouse.beerClub.service.interfaces.IAgeVerificationService;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class AgeVerificationService implements IAgeVerificationService {

    private final IAgeVerificationRepository ageVerificationRepository;
    private final Mapper mapper;

    public AgeVerificationService(IAgeVerificationRepository ageVerificationRepository, Mapper mapper) {
        this.ageVerificationRepository = ageVerificationRepository;
        this.mapper = mapper;
    }
    @Override
    public List<AgeVerificationDTO> searchAll() {
        return ageVerificationRepository.findAll()
                .stream().map(s -> mapper.converter(s, AgeVerificationDTO.class)).collect(Collectors.toList());
    }
    @Override
    public AgeVerificationDTO searchById(Long id) throws NotFoundException {
        return ageVerificationRepository.findById(id)
                .stream().map(s -> mapper.converter(s, AgeVerificationDTO.class)).findFirst()
                .orElseThrow(() -> new NotFoundException("AgeVerification not found"));
    }
    @Override
    public AgeVerificationDTO create(AgeVerificationAppDTO ageVerificationAppDTO) throws BadRequestException {
        int age = calculateAge(ageVerificationAppDTO.getDateOfBirth());
        AgeVerification ageVerification = new AgeVerification(ageVerificationAppDTO.getIp(), ageVerificationAppDTO.getCity(), ageVerificationAppDTO.getDateOfBirth(), age);
        ageVerificationRepository.save(ageVerification);
        return new AgeVerificationDTO(ageVerification);
    }

    protected int calculateAge(LocalDate dateOfBirth) {
        LocalDate currentDate = LocalDate.now();
        Period period = Period.between(dateOfBirth, currentDate);
        return period.getYears();
    }

}
