package com.digitalHouse.beerClub.controller;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.model.dto.RecommendationDTO;
import com.digitalHouse.beerClub.service.interfaces.IRecommendationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/recomendations")
@CrossOrigin("*")
public class RecommendationController {

    private final IRecommendationService recommendationService;

    public RecommendationController(IRecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @GetMapping
    public ResponseEntity<List<RecommendationDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(recommendationService.searchAll());
    }

    @PostMapping
    public ResponseEntity<RecommendationDTO> save (@RequestBody RecommendationDTO recommendationDTO) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(recommendationService.create(recommendationDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecommendationDTO> getById(@PathVariable
                                                    Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(recommendationService.searchById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecommendationDTO> update (@RequestBody RecommendationDTO recommendationDTO, @PathVariable Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(recommendationService.update(recommendationDTO,id));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> delete (@PathVariable Long id) throws ServiceException, NotFoundException {
        recommendationService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{subscriptionId}/{date}")
    public ResponseEntity<RecommendationDTO> getBySubscriptionAndDate (@PathVariable Long subscriptionId, @PathVariable LocalDate date) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(recommendationService.searchBySubscriptionAndDate(subscriptionId,date));
    }
}
