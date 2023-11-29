package com.digitalHouse.beerClub.service.interfaces;

import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.model.dto.ProductDTO;
import com.digitalHouse.beerClub.model.dto.ReviewDTO;

import java.util.List;

public interface IReviewService extends IService<ReviewDTO>{
    List<ProductDTO> getTopFiveProducts(Long userId) throws NotFoundException;
}
