package com.digitalHouse.beerClub.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    private final ModelMapper modelMapper;

    public Mapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <E> E converter(Object obj, Class<E> resultClass) {
        if (obj == null) {
            return null;
        }
        return modelMapper.map(obj, resultClass);
    }
}
