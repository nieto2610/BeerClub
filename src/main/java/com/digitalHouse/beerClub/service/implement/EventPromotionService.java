package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.EntityInactiveException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.EventPromotion;
import com.digitalHouse.beerClub.model.EventPromotionType;
import com.digitalHouse.beerClub.model.dto.EventPromotionDTO;
import com.digitalHouse.beerClub.model.dto.EventPromotionFilterDTO;
import com.digitalHouse.beerClub.repository.IEventPromotionRepository;
import com.digitalHouse.beerClub.service.interfaces.IEventPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventPromotionService implements IEventPromotionService {
    @Autowired
    private final IEventPromotionRepository eventPromotionRepository;
    @Autowired
    private final Mapper mapper;

    public EventPromotionService(IEventPromotionRepository eventPromotionRepository, Mapper mapper) {
        this.eventPromotionRepository = eventPromotionRepository;
        this.mapper = mapper;
    }

    @Override
    public List<EventPromotionDTO> searchAll() {
        return eventPromotionRepository.findAll()
                .stream().map(s -> mapper.converter(s, EventPromotionDTO.class)).collect(Collectors.toList());
    }

    @Override
    public EventPromotionDTO searchById(Long id) throws NotFoundException {
        EventPromotion eventPromotion = eventPromotionRepository.findById(id).orElseThrow(() -> new NotFoundException("Event or Promotion not found"));
        return mapper.converter(eventPromotion, EventPromotionDTO.class);
    }

    @Override
    public EventPromotionDTO create(EventPromotionDTO eventPromotionDTO) throws BadRequestException {
        EventPromotion eventPromotion = new EventPromotion();
        eventPromotion.setType(eventPromotionDTO.getType());
        eventPromotion.setTitle(eventPromotionDTO.getTitle());
        eventPromotion.setDescription(eventPromotionDTO.getDescription());
        eventPromotion.setImageUrl(eventPromotionDTO.getImageUrl());
        eventPromotion.setLocation(eventPromotionDTO.getLocation());
        eventPromotion.setIsActive(true);
        if(eventPromotionDTO.getType().equals(EventPromotionType.EVENT)){
            eventPromotion.setDateTime(eventPromotionDTO.getDateTime());
        }else{
            eventPromotion.setValidUntil(eventPromotionDTO.getValidUntil());
        }
        EventPromotion eventCreated = eventPromotionRepository.save(eventPromotion);
        return mapper.converter(eventCreated, EventPromotionDTO.class);
    }

    @Override
    public EventPromotionDTO update(EventPromotionDTO eventPromotionDTO, Long id) throws NotFoundException {
        EventPromotion eventPromotion = eventPromotionRepository.findById(id).orElseThrow(() -> new NotFoundException("Event or Promotion not found"));
        if(!eventPromotion.getIsActive()){
            throw new NotFoundException("The event or promotion is inactive");
        }
        eventPromotion.setType(eventPromotionDTO.getType());
        eventPromotion.setTitle(eventPromotionDTO.getTitle());
        eventPromotion.setDescription(eventPromotionDTO.getDescription());
        eventPromotion.setImageUrl(eventPromotionDTO.getImageUrl());
        eventPromotion.setLocation(eventPromotionDTO.getLocation());
        eventPromotion.setIsActive(true);
        if(eventPromotionDTO.getType().equals(EventPromotionType.EVENT)){
            eventPromotion.setDateTime(eventPromotionDTO.getDateTime());
        }else{
            eventPromotion.setValidUntil(eventPromotionDTO.getValidUntil());
        }
        EventPromotion eventUpdated = eventPromotionRepository.save(eventPromotion);
        return mapper.converter(eventUpdated, EventPromotionDTO.class);
    }

    @Override
    public void delete(Long id) throws ServiceException, NotFoundException {
        EventPromotion eventPromotion = eventPromotionRepository.findById(id).orElseThrow(() -> new NotFoundException("Event or Promotion not found"));
        if(!eventPromotion.getIsActive()){
            throw new NotFoundException("The event or promotion is inactive");
        }
        eventPromotion.setIsActive(false);
        eventPromotionRepository.save(eventPromotion);
    }

    @Override
    public List<EventPromotionDTO> filterEventPromotion(EventPromotionFilterDTO eventPromotionFilterDTO) {
        List<EventPromotion> filteredEventPromotions;
        LocalDate startDateTime = eventPromotionFilterDTO.getStartDateTime();
        LocalDate endDateTime = eventPromotionFilterDTO.getEndDateTime();
        LocalDate startValidUntil = eventPromotionFilterDTO.getStartValidUntil();
        LocalDate endValidUntil = eventPromotionFilterDTO.getEndValidUntil();
        String location = eventPromotionFilterDTO.getLocation();

        LocalDateTime startOfDay = startDateTime != null ? LocalDateTime.of(startDateTime, LocalTime.MIN) : null;
        LocalDateTime endOfDay = endDateTime != null ? LocalDateTime.of(endDateTime, LocalTime.MAX) : null;

        if(eventPromotionFilterDTO.getType().equals(EventPromotionType.EVENT)){
            filteredEventPromotions = eventPromotionRepository.findByFilters(startOfDay, endOfDay, null, null, location);
        }else{
            filteredEventPromotions = eventPromotionRepository.findByFilters(null, null, startValidUntil, endValidUntil, location);
        }
        return filteredEventPromotions.stream().map(s -> mapper.converter(s, EventPromotionDTO.class)).collect(Collectors.toList());
    }
}
