package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.*;
import com.digitalHouse.beerClub.model.dto.ProductDTO;
import com.digitalHouse.beerClub.model.dto.ReviewDTO;
import com.digitalHouse.beerClub.repository.IProductRepository;
import com.digitalHouse.beerClub.repository.IReviewRepository;
import com.digitalHouse.beerClub.repository.IUserRepository;
import com.digitalHouse.beerClub.service.interfaces.IReviewService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
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
        if(reviewDTO.getExistReview()){
            throw new BadRequestException("The review has already been created.");
        }
        Review review = new Review();
        User user= userRepository.findById(reviewDTO.getUserId()).orElseThrow(()->new BadRequestException("The user doesn't exist"));
        review.setUser(user);

        Product product= productRepository.findById(reviewDTO.getProductId()).orElseThrow(()-> new BadRequestException("Product information is missing"));
        review.setProduct(product);
        if(reviewDTO.getRating()==null){
            throw new BadRequestException("The rating can't be null");
        }
        if(reviewDTO.getRating()<1 || reviewDTO.getRating()>10){
            throw new BadRequestException("The rating must be a number between 1 and 10");
        }
        review.setRating(reviewDTO.getRating());
        review.setComments(reviewDTO.getComments());
        review.setExistReview(true);
        Review reviewCreated= reviewRepository.save(review);

        return mapper.converter(reviewCreated, ReviewDTO.class);

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

        else if(reviewA.getRating()<1 || reviewA.getRating()>10){
            throw new NotFoundException("The rating must be a number between 1 and 10");

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

    @Override
    public List<ProductDTO> getTopFiveProducts(Long userId) throws NotFoundException {
        User searchedUser = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
        if (searchedUser.isActive()) {
            throw new NotFoundException("The user is not active.");
        }

        List<Review> reviewList = reviewRepository.findByUserId(userId);
        if (reviewList.isEmpty()) {
            return new ArrayList<>();
        }

        int limit = Math.min(reviewList.size(), 5);

        List<ProductDTO> productDTOS = reviewList.stream()
                .sorted(Comparator.comparing(Review::getRating).reversed())
                .limit(limit)
                .map(review -> mapper.converter(review.getProduct(), ProductDTO.class))
                .collect(Collectors.toList());

        return productDTOS;

    }
}