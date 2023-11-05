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

    private final IRecommendationService recomendationService;

    public RecommendationController(IRecommendationService recomendationService) {
        this.recomendationService = recomendationService;
    }

    @GetMapping
    public ResponseEntity<List<RecommendationDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(recomendationService.searchAll());
    }

    @PostMapping
    public ResponseEntity<RecommendationDTO> save (@RequestBody RecommendationDTO recomendationDTO) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(recomendationService.create(recomendationDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecommendationDTO> getById(@PathVariable
                                                    Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(recomendationService.searchById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecommendationDTO> update (@RequestBody RecommendationDTO recomendationDTO, @PathVariable Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(recomendationService.update(recomendationDTO,id));
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<?> delete (@PathVariable Long id) throws ServiceException, NotFoundException {
        recomendationService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{subscriptionId}/{date}")
    public ResponseEntity<RecommendationDTO> getBySubscriptionAndDate (@PathVariable Long subscriptionId, @PathVariable LocalDate date) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(recomendationService.searchBySubscriptionAndDate(subscriptionId,date));
    }
}
