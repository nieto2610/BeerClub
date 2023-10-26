package com.digitalHouse.beerClub.service.Implement;

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


public class FaqsServiceTest {

    @InjectMocks
    private FaqsService faqsService;

    @Mock
    private IFaqsRepository faqsRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void searchAll() {

        when(faqsRepository.findAll()).thenReturn(List.of(new Faqs(), new Faqs(), new Faqs()));

        List<FaqsDTO> result = faqsService.searchAll();

        assertEquals(3, result.size());
    }

/*    @Test
    void searchById() throws NotFoundException {
        Long id = 1L;
        Faqs faqs = new Faqs();
        faqs.setId(id);

        when(faqsRepository.findById(id)).thenReturn(Optional.of(faqs));

        FaqsDTO result = faqsService.searchById(id);

        assertEquals(id, result.getId());
    }*/



    @Test
    void create() throws BadRequestException {
        FaqsDTO faqsDTO = new FaqsDTO();
        Faqs faqs = new Faqs();

        when(faqsRepository.save(any())).thenReturn(faqs);

        FaqsDTO result = faqsService.create(faqsDTO);

        assertNotNull(result);
    }
    @Test
    void update() throws NotFoundException {
        Long id = 1L;
        FaqsDTO faqsDTO = new FaqsDTO();
        faqsDTO.setQuestion("Q1");
        faqsDTO.setAnswer("A1");

        Mockito.when(faqsRepository.findById(id)).thenReturn(Optional.of(faqs));
        Mockito.when(faqsRepository.save(Mockito.any(Faqs.class))).thenReturn(faqs);

        FaqsDTO result = faqsService.update(faqs);

        assertEquals(id, result.getId());
        Mockito.verify(faqsRepository, Mockito.times(1)).findById(id);
        Mockito.verify(faqsRepository, Mockito.times(1)).save(Mockito.any(Faqs.class));
    }


    @Test
    void delete() throws NotFoundException {
        Long id = 1L;

        when(faqsRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> faqsService.delete(id));
    }
}