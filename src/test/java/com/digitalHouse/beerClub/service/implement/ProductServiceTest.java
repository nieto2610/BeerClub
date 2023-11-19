package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.Product;
import com.digitalHouse.beerClub.model.dto.ProductDTO;
import com.digitalHouse.beerClub.model.dto.RecommendationDTO;
import com.digitalHouse.beerClub.repository.IProductRepository;
import com.digitalHouse.beerClub.repository.IRecommendationRepository;
import com.digitalHouse.beerClub.repository.ISubscriptionRepository;
import com.digitalHouse.beerClub.service.implement.utils.DataGenerator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    DataGenerator dataGenerator;
    RecommendationService recommendationService;

    @Mock
    IProductRepository productRepository;
    @Mock
    ISubscriptionRepository subscriptionRepository;

    @Mock
    IRecommendationRepository recommendationRepository;
    @Mock
    Mapper mapper;

    ProductService productService;

    @BeforeEach
    void setUp() {
        recommendationService = new RecommendationService(recommendationRepository,subscriptionRepository,productRepository,mapper);
        dataGenerator = new DataGenerator();
        mapper = new Mapper(new ModelMapper());
        productService = new ProductService(productRepository,recommendationService,mapper);
    }

    @Test
    @DisplayName("✅ - Search global Top 10 of products")
    void searchAll() throws NotFoundException {
        //ARRANGE
        when(productRepository.findAll()).thenReturn(dataGenerator.getAllProducts());
        when(productRepository.save(any(Product.class))).thenReturn(new Product());

        //ACT
        List<ProductDTO> top10 = productService.getGlobalTopTen();

        //ASSERT
        Assertions.assertEquals(10, top10.size());
        top10.forEach(System.out::println);
    }

    @Test
    @DisplayName("✅ - Search global Top 10 of products -only 6")
    void searchTop10Only6() throws NotFoundException {
        //ARRANGE
        when(productRepository.findAll()).thenReturn(dataGenerator.getSixProducts());
        when(productRepository.save(any(Product.class))).thenReturn(new Product());

        //ACT
        List<ProductDTO> top10 = productService.getGlobalTopTen();

        //ASSERT
        Assertions.assertEquals(6, top10.size());
        top10.forEach(System.out::println);
    }

    @Test
    @DisplayName("✅ - Search global Top 10 of products - empty")
    void searchTop10Empty() throws NotFoundException {
        //ARRANGE
        when(productRepository.findAll()).thenReturn(new ArrayList<>());

        //ACT
        List<ProductDTO> top10 = productService.getGlobalTopTen();

        //ASSERT
        Assertions.assertTrue(top10.isEmpty());
    }


}
