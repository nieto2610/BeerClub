package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.Product;
import com.digitalHouse.beerClub.model.ProductImage;
import com.digitalHouse.beerClub.model.Recommendation;
import com.digitalHouse.beerClub.model.Subscription;
import com.digitalHouse.beerClub.model.dto.ProductDTO;
import com.digitalHouse.beerClub.model.dto.ProductImageDTO;
import com.digitalHouse.beerClub.model.dto.RecommendationDTO;
import com.digitalHouse.beerClub.model.dto.SubscriptionDTO;
import com.digitalHouse.beerClub.repository.IBenefitRepository;
import com.digitalHouse.beerClub.repository.IProductRepository;
import com.digitalHouse.beerClub.repository.IRecommendationRepository;
import com.digitalHouse.beerClub.repository.ISubscriptionRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {

    @Mock
    private IRecommendationRepository recommendationRepository;

    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @Mock
    private IProductRepository productRepository;

    @Mock
    private Mapper mapper;

    private RecommendationService recommendationService;

    @BeforeEach
    void setUp() {
        mapper = new Mapper(new ModelMapper());
        recommendationService = new RecommendationService(recommendationRepository,subscriptionRepository,productRepository,mapper);
    }

    @Test
    @DisplayName("✅ - Search all the recommendations")
    void searchAll() {
        //ARRANGE
        Recommendation recommendation = new Recommendation();
        recommendation.setId(1L);
        recommendation.setTitle("Recomendación test");
        recommendation.setDescription("Descripción");
        recommendation.setCreateDate(LocalDate.now());
        recommendation.setIsActive(true);
        recommendation.setProduct(new Product());
        recommendation.setImageUrl("http://www.fakepage.com/img/123");

        Set<Subscription> subscriptions = new HashSet<>();
        Subscription subscription = new Subscription();
        subscription.setId(1L);
        subscriptions.add(subscription);

        recommendation.setSubscriptions(subscriptions);

        List<Recommendation> expected = new ArrayList<>();
        expected.add(recommendation);

        when(recommendationRepository.findAll()).thenReturn(expected);

        //ACT
        List<RecommendationDTO> result = recommendationService.searchAll();

        //ASSERT
        Assertions.assertEquals(1L, result.get(0).getId());
    }

    @Test
    @DisplayName("✅ - Search recommendations by Id")
    void searchById() throws NotFoundException {
        //ARRANGE
        Long id = 1L;
        Recommendation recommendation = new Recommendation();
        recommendation.setId(id);
        recommendation.setTitle("Recomendación test");
        recommendation.setDescription("Descripción");
        recommendation.setCreateDate(LocalDate.now());
        recommendation.setIsActive(true);
        recommendation.setProduct(new Product());
        recommendation.setImageUrl("http://www.fakepage.com/img/123");



        when(recommendationRepository.findById(any())).thenReturn(Optional.of(recommendation));

        //ACT
        RecommendationDTO result = recommendationService.searchById(id);

        //ASSERT
        Assertions.assertEquals(id, result.getId());
    }

    @Test
    @DisplayName("❌ Not Found - Search recommendations by Id")
    void searchByIdNotFound() {
        //ARRANGE
        Long id = 1L;
        when(recommendationRepository.findById(any())).thenReturn(Optional.ofNullable(null));

        //ACT&ASSERT
        Assertions.assertThrows(NotFoundException.class,()->recommendationService.searchById(id));

    }

    @Test
    @DisplayName("✅ - Create recommendation")
    void create() throws BadRequestException {
        //ARRANGE
        Long id = 1L;
        RecommendationDTO expected = new RecommendationDTO();
        expected.setId(id);
        expected.setTitle("Recomendación test");
        expected.setDescription("Descripción");
        expected.setCreateDate(LocalDate.now());
        expected.setProduct(new ProductDTO());
        expected.setImageUrl("http://www.fakepage.com/img/123");

        expected.setSubscriptionId(1L);

        Subscription subscription1 = new Subscription();
        subscription1.setIsActive(true);

        Product product = new Product();
        product.setId(1L);

        Recommendation recommendation = new Recommendation();
        recommendation.setId(id);
        recommendation.setTitle("Recomendación test");
        recommendation.setDescription("Descripción");
        recommendation.setCreateDate(LocalDate.now());
        recommendation.setProduct(new Product());
        recommendation.setImageUrl("http://www.fakepage.com/img/123");

        Set<Subscription> subscriptions = new HashSet<>();
         Subscription subscription = new Subscription();
        subscription1.setIsActive(true);
        subscriptions.add(subscription);

        recommendation.setSubscriptions(subscriptions);


        when(subscriptionRepository.findById(any())).thenReturn(Optional.of(subscription1));
        when(productRepository.save(any(Product.class))).thenReturn(new Product());
        when(recommendationRepository.save(any())).thenReturn(recommendation);

        //ACT
        RecommendationDTO result = recommendationService.create(expected);

        //ASSERT
        Assertions.assertEquals(expected,result);
    }

    @Test
    @DisplayName("✅ - Update recommendation")
    void update() throws NotFoundException {
        //ARRANGE
        Long id = 1L;
        RecommendationDTO expected = new RecommendationDTO();
        expected.setId(id);
        expected.setTitle("Recomendación test");
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        expected.setProduct(productDTO);


        Recommendation recommendation = new Recommendation();
        recommendation.setId(id);
        recommendation.setTitle("Recomendación");
        recommendation.setDescription("Descripción");

        recommendation.setIsActive(true);
        Product product = new Product();
        product.setId(1L);
        product.setName("product Test");
        product.setRecommendationList(new ArrayList<>());
        product.setDescription("Descripción del producto");
        ProductImage pm = new ProductImage();
        pm.setId(id);
        pm.setUrl("www.fake.com/image");

        product.setImageUrl( List.of(pm));
        recommendation.setProduct(product);

        Set<Subscription> subscriptions = new HashSet<>();
        Subscription subscription = new Subscription();
        subscription.setId(id);
        subscription.setIsActive(true);
        subscriptions.add(subscription);
        recommendation.setSubscriptions(subscriptions);

        when(recommendationRepository.findById(any())).thenReturn(Optional.of(recommendation));
        when(subscriptionRepository.findById(any())).thenReturn(Optional.of(subscription));
        when(productRepository.save(any())).thenReturn(product);
        when(recommendationRepository.save(any())).thenReturn(recommendation);

        //ACT
        RecommendationDTO result = recommendationService.update(expected,id);

        //ASSERT
        Assertions.assertEquals(expected.getTitle(), "Recomendación test");
    }

    @Test
    @DisplayName("✅ - Soft Delete by Id")
    void delete() throws ServiceException, NotFoundException {
        //ARRANGE
        Long id = 1L;
        when(recommendationRepository.findById(any())).thenReturn(Optional.of(new Recommendation()));

        //ACT
        recommendationService.delete(id);

        //ASSERT
        verify(recommendationRepository, atLeastOnce()).findById(id);
    }

    @Test

    @DisplayName("✅ - Search a recommendation By subscription ID and Date")
    void searchBySubscriptionAndDate() throws NotFoundException {
        //ARRANGE
        Long id = 1L;

        LocalDate date = LocalDate.of(2023,11,10);
        Recommendation recommendation = new Recommendation();
        recommendation.setId(id);
        recommendation.setTitle("Recomendación test");
        recommendation.setDescription("Descripción");
        recommendation.setCreateDate(LocalDate.now());
        recommendation.setIsActive(true);
        recommendation.setProduct(new Product());
        recommendation.setImageUrl("http://www.fakepage.com/img/123");

        Set<Subscription> subscriptions = new HashSet<>();
        Subscription subscription = new Subscription();
        subscription.setId(id);
        subscription.setIsActive(true);
        subscriptions.add(subscription);

        recommendation.setSubscriptions(subscriptions);

        when(recommendationRepository.findBySubscriptionIdAndCreateDate(any(Long.class), any(int.class), any(int.class))).thenReturn(recommendation);

        //ACT
        RecommendationDTO result = recommendationService.searchBySubscriptionAndDate(id,date);

        //ASSERT
        Assertions.assertEquals(subscription.getId(),result.getSubscriptionId());
    }
}