package com.digitalHouse.beerClub.service.interfaces;

import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.model.dto.ProductDTO;

import java.util.List;

public interface IProductService extends IService<ProductDTO> {

    public List<ProductDTO> getGlobalTopTen() throws NotFoundException;
}
