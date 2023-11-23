package com.digitalHouse.beerClub.service.implement;
import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.Product;
import com.digitalHouse.beerClub.model.Review;
import com.digitalHouse.beerClub.model.Subscription;
import com.digitalHouse.beerClub.model.User;
import com.digitalHouse.beerClub.model.dto.ReviewDTO;
import com.digitalHouse.beerClub.model.dto.SubscriptionDTO;
import com.digitalHouse.beerClub.repository.IProductRepository;
import com.digitalHouse.beerClub.repository.IReviewRepository;
import com.digitalHouse.beerClub.repository.IUserRepository;
import com.digitalHouse.beerClub.service.implement.ReviewService;
import com.digitalHouse.beerClub.service.implement.utils.DataGenerator;
import com.digitalHouse.beerClub.service.interfaces.IReviewService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {
    DataGenerator dataGenerator;

    @Mock
    private IReviewRepository reviewRepository;

    @Mock
    private IProductRepository productRepository;

    @Mock
    private IUserRepository userRepository;

    @Mock
    private Mapper mapper;

    private IReviewService reviewService;


    @BeforeEach
    void setUp() {
        dataGenerator = new DataGenerator();
        mapper = new Mapper(new ModelMapper());
        reviewService = new ReviewService(reviewRepository, productRepository, userRepository,mapper);
    }
    @Test
    @DisplayName("✅ - Search all the review")
    void searchAll() {
        //ARRANGE

        Product product = new Product();
        product.setId(1L);
        User user =new User();
        user.setId(1L);
        Review review = new Review();
        review.setId(1L);
        review.setComments("Muy Bueno");
        review.setRating(5);
        review.setProduct(product);
        review.setUser(user);

        Review review1 = new Review();
        review1.setId(2L);
        review1.setComments("Muy Bueno");
        review1.setRating(5);
        review1.setProduct(product);
        review1.setUser(user);

        List<Review> expected = new ArrayList<>();

        expected.add(review);
        expected.add(review1);

        when(reviewRepository.findAll()).thenReturn(expected);

        //ACT
        List<ReviewDTO> result = reviewService.searchAll();

        //ASSERT
        Assertions.assertEquals(expected.size(), result.size());
    }
    @Test
    @DisplayName("✅ - Search review by id")
    void testSearchById() throws NotFoundException {
        //ARRANGE
        Long id = 1L;

        Product product = new Product();
        product.setId(1L);
        User user =new User();
        user.setId(id);
        Review review = new Review();
        review.setId(1L);
        review.setComments("Muy Bueno");
        review.setRating(5);
        review.setProduct(product);
        review.setUser(user);
        when(reviewRepository.findById(any())).thenReturn(of(review));

        //ACT
        ReviewDTO result = reviewService.searchById(id);

        //ASSERT
        Assertions.assertEquals(id, result.getId());
    }

    @Test
    @DisplayName("✅ - Create review")
    void create() throws BadRequestException {
        Long id = 1L;
        //ARRANGE
        Product product = new Product();
        product.setId(1L);
        product.setDescription("Corona Ale Pale");
        product.setName("Corona");
        product.setProductScore(4.0F);
        when(productRepository.findById(id)).thenReturn(of(product));

        User user =new User();
        user.setId(1L);
        user.setActive(true);
        user.setEmail("Lalala123@gmail.com");
        user.setPassword("$123456Aa");
        user.setFirstName("Sofia");
        user.setLastName("Acosta");
        user.setTelephone("123456789");
        user.setSubscription(new Subscription());
        when(userRepository.findById(id)).thenReturn(of(user));

        ReviewDTO expected = new ReviewDTO();
        expected.setId(1L);
        expected.setComments("Muy Bueno");
        expected.setRating(5);
        expected.setProductId(id);
        expected.setUserId(id);

        Review review = new Review();
        review.setId(1L);
        review.setComments("Muy Bueno");
        review.setRating(5);
        review.setUser(user);
        review.setProduct(product);
        when(reviewRepository.save(any())).thenReturn(review);

        //ACT
        ReviewDTO result = reviewService.create(expected);

        //ASSERT
        assertEquals(id, result.getId());
        assertEquals(expected.getProductId(), result.getProductId());
        assertEquals(expected.getComments(), result.getComments());
        assertEquals(expected.getUserId(), result.getUserId());
        assertEquals(expected.getRating(), result.getRating());

    }


    @Test
    @DisplayName("✅ - Delete review")
    void testDelete() throws ServiceException, NotFoundException {
        // Arrange
        Long id = 1L;
        Review review = new Review();
        when(reviewRepository.findById(id)).thenReturn(of(review));

        // Act
        reviewService.delete(id);

        // Assert
        verify(reviewRepository).findById(id);
        verify(reviewRepository).delete(review);
    }
}