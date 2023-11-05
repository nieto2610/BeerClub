package com.digitalHouse.beerClub.controller;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.model.dto.ReviewDTO;
import com.digitalHouse.beerClub.service.implement.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@Validated
@RestController
@RequestMapping("reviews")
public class ReviewController {
    final static Logger logger = Logger.getLogger(String.valueOf(ReviewController.class));
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping
    public List<ReviewDTO> searchAll() {
        logger.info("We are listing all recent reviews");
        return reviewService.searchAll();
    }

    @PostMapping
    public ReviewDTO save(@RequestBody ReviewDTO reviewDTO) throws BadRequestException {
        logger.info("We are saving the new review");
        return reviewService.create(reviewDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getById(@PathVariable Long id) throws NotFoundException {
        logger.info("We are looking for the review which id: " + id);
        return  ResponseEntity.status(HttpStatus.OK).body(reviewService.searchById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> update(@RequestBody ReviewDTO reviewDTO, @PathVariable Long id) throws NotFoundException {
        logger.info("We are updating the review which id: " + id);
        return ResponseEntity.status(HttpStatus.OK).body(reviewService.update(reviewDTO,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReviewDTO> delete (@PathVariable Long id) throws NotFoundException, ServiceException {
        logger.info("We are deleting the id Review: " + id);
        reviewService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
