package com.digitalHouse.beerClub.service.interfaces;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;

import java.util.List;

public interface IService<T> {
    List<T> searchAll();
    T searchById(Long id) throws NotFoundException;
    T create(T entity) throws BadRequestException;
    T update(T entity, Long id) throws NotFoundException;
<<<<<<< HEAD
    void delete(Long id) throws ServiceException, NotFoundException;
=======
    void delete(Long id) throws NotFoundException;
>>>>>>> d495d3a55f4b7568650a19839750178fbb781077
}
