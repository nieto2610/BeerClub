package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.Review;

import com.digitalHouse.beerClub.model.dto.ReviewDTO;

import com.digitalHouse.beerClub.repository.IReviewRepository;

import com.digitalHouse.beerClub.service.interfaces.IReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class ReviewService implements IReviewService{
    private final IReviewRepository reviewRepository;
    private final Mapper mapper;

    public ReviewService(IReviewRepository reviewRepository, Mapper mapper) {
        this.reviewRepository = reviewRepository;
        this.mapper = mapper;
    }

    @Override
    public List<ReviewDTO> searchAll() {
        return reviewRepository.findAll()
                .stream().map(s -> mapper.converter(s, ReviewDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ReviewDTO searchById(Long id) throws NotFoundException {
        return reviewRepository.findById(id)
                .stream().map(s -> mapper.converter(s, ReviewDTO.class)).findFirst()
                .orElseThrow(() -> new NotFoundException("Review not found"));
    }

    @Override
    public ReviewDTO create(ReviewDTO reviewDTO) throws BadRequestException {
        Review review = reviewRepository.save(mapper.converter(reviewDTO, Review.class));
        return mapper.converter(review, ReviewDTO.class);
    }

    @Override
    public ReviewDTO update(ReviewDTO reviewDTO, Long id) throws NotFoundException {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("Review not found"));
        Review reviewA=mapper.converter(reviewDTO,Review.class);
        reviewRepository.save(reviewA);
        return mapper.converter(review, ReviewDTO.class);
    }
    @Override
    public void delete(Long id) throws ServiceException, NotFoundException {
        Review review= reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("Review not found"));
        reviewRepository.delete(review);
    }
}