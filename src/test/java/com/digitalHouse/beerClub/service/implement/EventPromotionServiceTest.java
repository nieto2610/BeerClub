package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.EventPromotion;
import com.digitalHouse.beerClub.model.EventPromotionType;
import com.digitalHouse.beerClub.model.dto.EventPromotionDTO;
import com.digitalHouse.beerClub.repository.IEventPromotionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventPromotionServiceTest {
    @Mock
    private IEventPromotionRepository eventPromotionRepository;
    @Mock
    private Mapper mapper;
    private EventPromotionService eventPromotionService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        eventPromotionService = new EventPromotionService(eventPromotionRepository, mapper);
    }

    @Test
    void searchAll() {
        List<EventPromotion> eventPromotions = new ArrayList<>();

        EventPromotion event = createEvent();
        EventPromotion discount = createDiscount();

        eventPromotions.add(event);
        eventPromotions.add(discount);

        when(eventPromotionRepository.findAll()).thenReturn(eventPromotions);

        when(mapper.converter(event, EventPromotionDTO.class))
                .thenReturn(new EventPromotionDTO(event.getId(), event.getType(), event.getTitle(), event.getDescription(), event.getImageUrl(), event.getLocation(), event.getDateTime(), null, event.getIsActive()));


        List<EventPromotionDTO> eventPromotionDTOS = eventPromotionService.searchAll();
        EventPromotionDTO eventDTO = eventPromotionDTOS.get(0);
        if(eventDTO != null){
            assertEquals(1L, eventDTO.getId());
            assertEquals("OctoberFest", eventDTO.getTitle());
            assertEquals("Mes de la cerveza", eventDTO.getDescription());
            assertEquals("Centro de Eventos Norte - Bogotá", eventDTO.getLocation());
            assertEquals(true, eventDTO.getIsActive());
        }

    }

    @Test
    void searchById() throws NotFoundException {
        EventPromotion event = createEvent();

        when(eventPromotionRepository.findById(1L)).thenReturn(Optional.of(event));
        when(mapper.converter(event, EventPromotionDTO.class))
                .thenReturn(new EventPromotionDTO(event.getId(), event.getType(), event.getTitle(), event.getDescription(), event.getImageUrl(), event.getLocation(), event.getDateTime(), null, event.getIsActive()));

        EventPromotionDTO eventPromotionDTO = eventPromotionService.searchById(1L);

        if(eventPromotionDTO != null){
            assertEquals(1L, eventPromotionDTO.getId());
            assertEquals("OctoberFest", eventPromotionDTO.getTitle());
            assertEquals("Mes de la cerveza", eventPromotionDTO.getDescription());
            assertEquals("Centro de Eventos Norte - Bogotá", eventPromotionDTO.getLocation());
            assertEquals(true, eventPromotionDTO.getIsActive());
        }

    }

    @Test
    void create() throws BadRequestException {
        EventPromotion event = createEvent();

        when(eventPromotionRepository.save(any(EventPromotion.class))).thenReturn(event);
        when(mapper.converter(event, EventPromotionDTO.class)).thenReturn(new EventPromotionDTO(event.getId(), event.getType(), event.getTitle(), event.getDescription(), event.getImageUrl(), event.getLocation(), event.getDateTime(), null, event.getIsActive()));

        EventPromotionDTO createdEventDTO = eventPromotionService.create(mapper.converter(event, EventPromotionDTO.class));

        verify(eventPromotionRepository, times(1)).save(any(EventPromotion.class));

        if(createdEventDTO != null){
            assertEquals(1L, createdEventDTO.getId());
            assertEquals(1L, createdEventDTO.getId());
            assertEquals("OctoberFest", createdEventDTO.getTitle());
            assertEquals("Mes de la cerveza", createdEventDTO.getDescription());
            assertEquals("Centro de Eventos Norte - Bogotá", createdEventDTO.getLocation());
            assertEquals(true, createdEventDTO.getIsActive());
        }
    }

    @Test
    void update() throws NotFoundException, BadRequestException {
        EventPromotion event = createEvent();

        when(eventPromotionRepository.findById(1L)).thenReturn(Optional.of(event));
        when(mapper.converter(event, EventPromotionDTO.class)).thenReturn(new EventPromotionDTO(event.getId(), event.getType(), event.getTitle(), event.getDescription(), event.getImageUrl(), event.getLocation(), event.getDateTime(), null, event.getIsActive()));

        EventPromotionDTO eventPromotionDTO = eventPromotionService.searchById(1L);
        eventPromotionDTO.setLocation("Centro de Eventos Sur - Bogotá");

        when(eventPromotionRepository.save(any(EventPromotion.class))).thenReturn(event);
        when(mapper.converter(eventPromotionDTO, EventPromotion.class)).thenReturn(new EventPromotion(eventPromotionDTO.getId(), eventPromotionDTO.getType(), eventPromotionDTO.getTitle(), eventPromotionDTO.getDescription(), eventPromotionDTO.getImageUrl(), eventPromotionDTO.getLocation(), eventPromotionDTO.getDateTime(), null, eventPromotionDTO.getIsActive()));

        EventPromotionDTO createdEventDTO = eventPromotionService.create(mapper.converter(event, EventPromotionDTO.class));

        verify(eventPromotionRepository, times(1)).save(any(EventPromotion.class));

        if(createdEventDTO != null){
            assertEquals(1L, createdEventDTO.getId());
            assertEquals(1L, createdEventDTO.getId());
            assertEquals("OctoberFest", createdEventDTO.getTitle());
            assertEquals("Mes de la cerveza", createdEventDTO.getDescription());
            assertEquals("Centro de Eventos Sur - Bogotá", createdEventDTO.getLocation());
            assertEquals(true, createdEventDTO.getIsActive());
        }

    }

    @Test
    void delete() throws ServiceException, NotFoundException {
        EventPromotion event = createEvent();

        when(eventPromotionRepository.findById(1L)).thenReturn(Optional.of(event));
        when(mapper.converter(event, EventPromotionDTO.class)).thenReturn(new EventPromotionDTO(event.getId(), event.getType(), event.getTitle(), event.getDescription(), event.getImageUrl(), event.getLocation(), event.getDateTime(), null, event.getIsActive()));
        when(eventPromotionRepository.save(event)).thenReturn(event);

        eventPromotionService.delete( 1L);
        verify(eventPromotionRepository, times(1)).save(any(EventPromotion.class));

    }

    private EventPromotion createEvent(){
        EventPromotion event = new EventPromotion();
        event.setId(1L);
        event.setType(EventPromotionType.EVENT);
        event.setTitle("OctoberFest");
        event.setDescription("Mes de la cerveza");
        event.setImageUrl("https://unsplash.com/es/fotos/cinco-jarras-de-cerveza-llenas-de-cerveza-gvm_Kmm3-9o");
        event.setLocation("Centro de Eventos Norte - Bogotá");
        event.setDateTime(LocalDateTime.of(2024,9,30,16,0,0 ));
        event.setIsActive(true);
        return event;
    }

    private EventPromotion createDiscount(){
        EventPromotion discount = new EventPromotion();
        discount.setId(1L);
        discount.setType(EventPromotionType.DISCOUNT);
        discount.setTitle("BBC Beer");
        discount.setDescription("10% de descuento en la primera compra");
        discount.setImageUrl("https://unsplash.com/es/fotos/persona-que-sostiene-la-jarra-de-cerveza-bajo-el-grifo-del-barril-E9Vk2OiG23U");
        discount.setLocation("BBC - Medellín");
        discount.setValidUntil(LocalDate.of(2024,1,31));
        discount.setIsActive(true);
        return discount;
    }

    private EventPromotionDTO mapToDTO(EventPromotion eventPromotion){
        return mapper.converter(eventPromotion, EventPromotionDTO.class);
    }
}