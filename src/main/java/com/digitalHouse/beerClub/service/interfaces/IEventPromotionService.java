package com.digitalHouse.beerClub.service.interfaces;

import com.digitalHouse.beerClub.model.dto.EventPromotionDTO;
import com.digitalHouse.beerClub.model.dto.EventPromotionFilterDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IEventPromotionService extends IService<EventPromotionDTO>{
    public List<EventPromotionDTO> filterEventPromotion(EventPromotionFilterDTO eventPromotionFilterDTO);
}
