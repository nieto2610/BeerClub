package com.digitalHouse.beerClub.service.Implement;

import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.AgeVerification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.model.Faqs;
import com.digitalHouse.beerClub.model.dto.FaqsDTO;
import com.digitalHouse.beerClub.repository.IFaqsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class FaqsServiceTest {

    @Mock
    private IFaqsRepository faqsRepository;
    @Mock
    private Mapper mapper;
    private FaqsService faqsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        faqsService = new FaqsService(faqsRepository, mapper);
    }

    @Test
    void searchAll() {

        when(faqsRepository.findAll()).thenReturn(List.of(new Faqs(), new Faqs(), new Faqs()));

        List<FaqsDTO> result = faqsService.searchAll();

        assertEquals(3, result.size());
    }

    @Test
    void searchById() throws NotFoundException {
        Long id = 1L;
        Faqs faqs = new Faqs();
        faqs.setId(id);
        faqs.setQuestion("Q1");
        faqs.setAnswer("A1");

        when(faqsRepository.findById(id)).thenReturn(Optional.of(faqs));
        when(mapper.converter(faqs, FaqsDTO.class)).thenReturn(new FaqsDTO(faqs.getId(), faqs.getQuestion(), faqs.getAnswer()));

        FaqsDTO result = faqsService.searchById(id);

        assertEquals(id, result.getId());
    }

    /*
    @Test
    void create() throws BadRequestException {
        Long id = 1L;
        FaqsDTO faqsDTO = new FaqsDTO();
        faqsDTO.setQuestion("Q1");
        faqsDTO.setAnswer("A1");

        when(faqsRepository.save(org.mockito.ArgumentMatchers.any(Faqs.class)))
                .thenReturn(new Faqs(id, faqsDTO.getQuestion(), faqsDTO.getAnswer()));

        FaqsDTO result = faqsService.create(faqsDTO);

        assertNotNull(result);
        assertEquals(id, result.getId());
    }
    @Test
    void update() throws NotFoundException {
        Long id = 1L;
        Faqs faqs = new Faqs();
        faqs.setQuestion("Q1");
        faqs.setAnswer("A1");

        FaqsDTO faqsDTO = new FaqsDTO();
        faqsDTO.setId(1L);
        faqsDTO.setQuestion("Q1");
        faqsDTO.setAnswer("A1");

        Mockito.when(faqsRepository.findById(id)).thenReturn(Optional.of(faqs));
        Mockito.when(faqsRepository.save(Mockito.any(Faqs.class))).thenReturn(faqs);

        FaqsDTO result = faqsService.update(faqsDTO, faqsDTO.getId());

        assertEquals(id, result.getId());
    }*/


    @Test
    void delete() throws NotFoundException {
        Long id = 1L;

        when(faqsRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> faqsService.delete(id));
    }
}