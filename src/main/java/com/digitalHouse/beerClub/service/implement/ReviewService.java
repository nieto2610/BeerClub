package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.*;
import com.digitalHouse.beerClub.model.dto.ReviewDTO;
import com.digitalHouse.beerClub.repository.IProductRepository;
import com.digitalHouse.beerClub.repository.IReviewRepository;
import com.digitalHouse.beerClub.repository.IUserRepository;
import com.digitalHouse.beerClub.service.interfaces.IReviewService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class ReviewService implements IReviewService{
    private final IReviewRepository reviewRepository;
    private final IProductRepository productRepository;
    private final IUserRepository userRepository;
    private final Mapper mapper;


    public ReviewService(IReviewRepository reviewRepository, IProductRepository productRepository, IUserRepository userRepository, Mapper mapper) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
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
        Review review = mapper.converter(reviewDTO, Review.class);
        Optional<Product> product= productRepository.findById(review.getProduct().getId());

        if (product.isEmpty()) {
            throw new BadRequestException("Product information is missing");
        }

        else if(!userRepository.existsById(review.getUser().getId())) {
            throw new BadRequestException("The user doesn't exist");
        }

        else if(review.getRating()<1 || review.getRating()>5){
                throw new BadRequestException("The rating must be a number between 1 and 5");

        } else {
            reviewRepository.save(review);
            return mapper.converter(review, ReviewDTO.class);
        }
    }
    @Override
    public ReviewDTO update(ReviewDTO reviewDTO, Long id) throws NotFoundException {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("Review not found"));
        Review reviewA=mapper.converter(reviewDTO,Review.class);
        Optional<Product> product= productRepository.findById(reviewA.getProduct().getId());

        if (product.isEmpty()) {
            throw new NotFoundException("Product information is missing");
        }

        else if(!userRepository.existsById(reviewA.getUser().getId())) {
            throw new NotFoundException("The user doesn't exist");
        }

        else if(reviewA.getRating()<1 || reviewA.getRating()>5){
            throw new NotFoundException("The rating must be a number between 1 and 5");

        } else {
            reviewRepository.save(reviewA);
            return mapper.converter(reviewA, ReviewDTO.class);
        }
    }
    @Override
    public void delete(Long id) throws ServiceException, NotFoundException {
        Review review= reviewRepository.findById(id).orElseThrow(() -> new NotFoundException("Review not found"));
        reviewRepository.delete(review);
    }
}