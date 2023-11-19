package com.digitalHouse.beerClub.service.implement.utils;

import com.digitalHouse.beerClub.model.Product;
import com.digitalHouse.beerClub.model.Review;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;


public class DataGenerator {

    List<Product> products;


    public DataGenerator() {
        this.products = getAllProducts();
    }

    Integer[] rating = {4, 5 ,5 , 3, 2, 1, 3, 5, 4, 3, 3, 2};

    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        for (Long i = Long.valueOf(1); i < 12; i++){
            products.add(generateProduct(i));
        }
        return  products;
    }

    public  List<Product> getSixProducts() {
        return products.subList(0,6);
    }

    public Product generateProduct(Long index){
        Product product = new Product();
        product.setId(index);
        product.setName("Cerveza " + index.toString());
        product.setDescription("Descripción cerveza " + index);
        product.setProductScore(0F);

        List<Review> reviewList = new ArrayList<>();
        for (int i = 1; i <= 5; i++){
            reviewList.add(generateReview(index));
        }
        product.setReviewList(reviewList);

        return product;
    }

    public Review generateReview(Long index){
        Review review = new Review();
        review.setId(index);
        review.setComments("Comentarios sobre la cerveza " + index);
        review.setRating(rating[Math.toIntExact(index)]);
        return  review;
    }


}
