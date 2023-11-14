package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.Product;
import com.digitalHouse.beerClub.model.Recommendation;
import com.digitalHouse.beerClub.model.Review;
import com.digitalHouse.beerClub.model.Subscription;
import com.digitalHouse.beerClub.model.dto.ProductDTO;
import com.digitalHouse.beerClub.model.dto.RecommendationDTO;
import com.digitalHouse.beerClub.repository.IProductRepository;
import com.digitalHouse.beerClub.repository.IRecommendationRepository;
import com.digitalHouse.beerClub.repository.ISubscriptionRepository;
import com.digitalHouse.beerClub.service.interfaces.IRecommendationService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class RecommendationService implements IRecommendationService {

    private  final IRecommendationRepository recommendationRepository;
    private final ISubscriptionRepository subscriptionRepository;
    private final IProductRepository productRepository;
    private final Mapper mapper;

    public RecommendationService(IRecommendationRepository recommendationRepository, ISubscriptionRepository subscriptionRepository, IProductRepository productRepository, Mapper mapper) {
        this.recommendationRepository = recommendationRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public List<RecommendationDTO> searchAll() {
        return recommendationRepository.findAll().stream().filter(Recommendation::getIsActive)
                .map(r -> {
                    RecommendationDTO recommendationDTO = mapper.converter(r, RecommendationDTO.class);
                    // Obtener el primer subscriptionId si existe
                    Optional<Subscription> firstSubscription = r.getSubscriptions().stream().findFirst();
                    firstSubscription.ifPresent(subscription -> recommendationDTO.setSubscriptionId(subscription.getId()));
                    return recommendationDTO;
                })
                .toList();
    }

    @Override
    public RecommendationDTO searchById(Long id) throws NotFoundException {
        Recommendation recommendation = recommendationRepository.findById(id).orElseThrow(()-> new NotFoundException("Recommendation not found"));

        return mapper.converter(recommendation, RecommendationDTO.class);
    }

    @Override
    public RecommendationDTO create(RecommendationDTO recommendationDTO) throws BadRequestException {

        Subscription subscription = subscriptionRepository.findById(recommendationDTO.getSubscriptionId()).orElseThrow(()-> new BadRequestException("Subscription not found"));

        if(!subscription.getIsActive()){
            throw new BadRequestException("Subscription not found");
        }

        Recommendation recommendation = mapper.converter(recommendationDTO, Recommendation.class);
        recommendation.setIsActive(true);
        recommendation.setCreateDate(LocalDate.now());

        Set<Subscription> subscriptions = new HashSet<>();
        subscriptions.add(subscription);
        recommendation.setSubscriptions(subscriptions);

        ProductDTO productDTO = recommendationDTO.getProduct();
        if (productDTO == null) {
            throw new BadRequestException("Product information is missing");
        }

        Product product = mapper.converter(productDTO, Product.class);
        Product  newProduct = productRepository.save(product);
        recommendation.setProduct(newProduct);

        Recommendation newRecommendation = recommendationRepository.save(recommendation);

        RecommendationDTO recommendationResponse= mapper.converter(newRecommendation, RecommendationDTO.class);

        recommendationResponse.setSubscriptionId(recommendationDTO.getSubscriptionId());

        return  recommendationResponse;
    }

    @Override
    public RecommendationDTO update(RecommendationDTO recommendationDTO, Long id) throws NotFoundException {
        Recommendation recommendation = recommendationRepository.findById(id).orElseThrow(()-> new NotFoundException("Recommendation not found"));

        if(!recommendation.getIsActive()){
            throw new NotFoundException("Recommendation not found");
        }

        Subscription subscription = subscriptionRepository.findById(recommendationDTO.getSubscriptionId()).orElseThrow(()-> new NotFoundException("Subscription not found"));

        recommendation.setTitle(recommendationDTO.getTitle());
        recommendation.setDescription(recommendationDTO.getDescription());
        recommendation.setCreateDate(recommendationDTO.getCreateDate());
        recommendation.setImageUrl(recommendationDTO.getImageUrl());

        ProductDTO productDTO = recommendationDTO.getProduct();
        if (productDTO == null) {
            throw new NotFoundException("Product information is missing");
        }
        Product product = mapper.converter(productDTO, Product.class);
        Product  newProduct = productRepository.save(product);
        recommendation.setProduct(newProduct);

        recommendation.getSubscriptions().add(subscription);

        Recommendation newRecommendation = recommendationRepository.save(recommendation);
        RecommendationDTO recommendationResponse= mapper.converter(newRecommendation, RecommendationDTO.class);
        recommendationResponse.setSubscriptionId(recommendationDTO.getSubscriptionId());

        return  recommendationResponse;
    }

    @Override
    public void delete(Long id) throws ServiceException, NotFoundException {
        Recommendation recommendation = recommendationRepository.findById(id).orElseThrow(()-> new NotFoundException("Recommendation not found"));

        recommendation.setIsActive(false);
        recommendationRepository.save(recommendation);
    }

    @Override
    public RecommendationDTO searchBySubscriptionAndDate(Long subscriptionId, LocalDate date) throws NotFoundException {
        Float productScore= (float) 0.0;
        Recommendation recommendation = recommendationRepository.findBySubscriptionIdAndCreateDate(subscriptionId,date.getMonthValue(), date.getYear());
        if(recommendation == null) {
            throw new NotFoundException("Recommendation not found");
        }
        Product product= recommendation.getProduct();
        List<Review> reviewListProduct= recommendation.getProduct().getReviewList();
        for(Review r :reviewListProduct) {
            productScore+=r.getRating();
        }
        productScore = productScore/reviewListProduct.size();
        product.setProductScore(productScore);
        recommendation.setProduct(product);

        RecommendationDTO recommendationDTO = mapper.converter(recommendation,RecommendationDTO.class);
        recommendationDTO.setSubscriptionId(subscriptionId);
        return recommendationDTO;
    }
}
