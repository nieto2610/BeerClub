package com.digitalHouse.beerClub.service.interfaces;

import java.util.List;

public interface IService<T> {
    List<T> searchAll();
    T searchById(Long id) throws NotFoundException;
    T create(T entity) throws BadRequestException;
    T update(T entity, Long id) throws NotFoundException;
    void delete(Long id) throws ServiceException, NotFoundException;
}
