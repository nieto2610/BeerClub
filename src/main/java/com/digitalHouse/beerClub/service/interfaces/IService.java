package com.digitalHouse.beerClub.service.interfaces;

import com.digitalHouse.beerClub.exeptions.BadRequestException;
import com.digitalHouse.beerClub.exeptions.NotFoundException;
import com.digitalHouse.beerClub.exeptions.ServiceException;

import java.util.List;

public interface IService<T> {
    List<T> searchAll() throws NotFoundException;
    T searchById(Long id) throws NotFoundException;
    T create(T entity) throws BadRequestException;
    T update(T entity, Long id) throws NotFoundException;
    void delete(Long id) throws ServiceException, NotFoundException;
}
