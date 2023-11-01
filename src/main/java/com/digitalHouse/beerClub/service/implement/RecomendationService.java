package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.Recomendation;
import com.digitalHouse.beerClub.model.dto.RecomendationDTO;
import com.digitalHouse.beerClub.repository.IRecomendationRepository;
import com.digitalHouse.beerClub.repository.ISubscriptionRepository;
import com.digitalHouse.beerClub.service.interfaces.IRecomendationService;

import java.util.List;

public class RecomendationService implements IRecomendationService {

    private  final IRecomendationRepository recomendationRepository;
    private final ISubscriptionRepository subscriptionRepository;
    private final Mapper mapper;

    public RecomendationService(IRecomendationRepository recomendationRepository, ISubscriptionRepository subscriptionRepository, Mapper mapper) {
        this.recomendationRepository = recomendationRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.mapper = mapper;
    }

    @Override
    public List<RecomendationDTO> searchAll() {
        return recomendationRepository.findAll().stream().map(r -> mapper.converter(r,RecomendationDTO.class)).toList();
    }

    @Override
    public RecomendationDTO searchById(Long id) throws NotFoundException {
        return null;
    }

    @Override
    public RecomendationDTO create(RecomendationDTO entity) throws BadRequestException {
        return null;
    }

    @Override
    public RecomendationDTO update(RecomendationDTO entity, Long id) throws NotFoundException {
        return null;
    }

    @Override
    public void delete(Long id) throws ServiceException, NotFoundException {

    }
}
