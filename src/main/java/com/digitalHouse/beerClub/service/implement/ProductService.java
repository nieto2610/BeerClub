package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.Product;
import com.digitalHouse.beerClub.model.dto.ProductDTO;
import com.digitalHouse.beerClub.repository.IProductRepository;
import com.digitalHouse.beerClub.service.interfaces.IProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final RecommendationService recommendationService;
    private final Mapper mapper;

    public ProductService(IProductRepository productRepository, RecommendationService recommendationService, Mapper mapper) {
        this.productRepository = productRepository;
        this.recommendationService = recommendationService;
        this.mapper = mapper;
    }

    @Override
    public List<ProductDTO> searchAll() {
        return null;
    }

    @Override
    public ProductDTO searchById(Long id) throws NotFoundException {
        return null;
    }

    @Override
    public ProductDTO create(ProductDTO entity) throws BadRequestException {
        return null;
    }

    @Override
    public ProductDTO update(ProductDTO entity, Long id) throws NotFoundException {
        return null;
    }

    @Override
    public void delete(Long id) throws ServiceException, NotFoundException {

    }

    public List<ProductDTO> getGlobalTopTen() throws NotFoundException {
        List<Product> productList = productRepository.findAll();

        if(productList.isEmpty()){
            throw new NotFoundException("there are no products for ranking");
        }

        for (Product p: productList) {
            p.setProductScore(recommendationService.getProductAverage(p.getReviewList()));
            if(p.getProductScore() == null || p.getProductScore().isNaN()) {
                p.setProductScore(0.0F);
                productRepository.save(p);
            }else {
                productRepository.save(p);
            }
        }
        productList.sort(Comparator.comparing(Product::getProductScore).reversed());

        List<ProductDTO> topTen = new ArrayList<>();

        for (int index  = 0 ; index < productList.size() && index < 10 ; index++){

            topTen.add(mapper.converter(productList.get(index), ProductDTO.class));
        }

        return topTen;
    }
}
