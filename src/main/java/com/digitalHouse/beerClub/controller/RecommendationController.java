package com.digitalHouse.beerClub.controller;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.model.dto.RecommendationDTO;
import com.digitalHouse.beerClub.service.interfaces.IRecommendationService;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Validated
@RestController
@RequestMapping("/recomendations")
@CrossOrigin("*")
@Tag(name = "Recommendations")
public class RecommendationController {

    private final IRecommendationService recommendationService;

    public RecommendationController(IRecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @Operation(summary="List all recommendations", description="List all recommendations", responses = {
    @ApiResponse(responseCode = "200",description = "OK",content = @Content(mediaType = "application/json",schema = @Schema(implementation = RecommendationDTO.class)))})
    @GetMapping
    public ResponseEntity<List<RecommendationDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(recommendationService.searchAll());
    }

    @Operation(summary="Add recommendation", description="Add a new recommendation", responses = {
            @ApiResponse(responseCode = "201",description = "CREATED",content = @Content(mediaType = "application/json",schema = @Schema(implementation = RecommendationDTO.class)))})
    @PostMapping
    public ResponseEntity<RecommendationDTO> save (@RequestBody @Valid RecommendationDTO recommendationDTO) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(recommendationService.create(recommendationDTO));
    }

    @Operation(summary ="Find recommendation by ID", description ="Find recommendation by ID", responses = {
            @ApiResponse(responseCode = "200",description = "OK",content = @Content(mediaType = "application/json",schema = @Schema(implementation = RecommendationDTO.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND", content = @Content(mediaType = "text/plain", schema = @Schema(defaultValue= "Recommendation not found")))})
    @GetMapping("/{id}")
    public ResponseEntity<RecommendationDTO> getById(@PathVariable @Positive(message = "Id must be greater than 0")
                                                    Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(recommendationService.searchById(id));
    }

    @Operation(summary = "Update a recommendation", description = "Update an existing recommendation", responses = {
            @ApiResponse(responseCode = "200",description = "OK",content = @Content(mediaType = "application/json",schema = @Schema(implementation = RecommendationDTO.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND", content = @Content(mediaType = "text/plain", schema = @Schema(defaultValue= "Recommendation not found")))})
    @PutMapping("/{id}")
    public ResponseEntity<RecommendationDTO> update (@RequestBody @Valid RecommendationDTO recommendationDTO, @PathVariable @Positive(message = "Id must be greater than 0") Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(recommendationService.update(recommendationDTO,id));
    }

    @Operation(summary = "Delete recommendation", description = "Delete a recommendation by ID",responses = {
            @ApiResponse(responseCode = "204",description = "NO_CONTENT",content = @Content(mediaType = "text/plain",schema = @Schema(defaultValue = "1"))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND", content = @Content(mediaType = "text/plain", schema = @Schema(defaultValue= "Recommendation not found")))})
    @DeleteMapping("/{id}")
    public  ResponseEntity<?> delete (@PathVariable @Positive(message = "Id must be greater than 0") Long id) throws ServiceException, NotFoundException {
        recommendationService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Find a recommendation by subscription ID and date", description = "Find a recommendation by subscription ID and date",responses = {
            @ApiResponse(responseCode = "200",description = "OK",content = @Content(mediaType = "application/json",schema = @Schema(implementation = RecommendationDTO.class))),
            @ApiResponse(responseCode = "404", description = "NOT_FOUND", content = @Content(mediaType = "text/plain", schema = @Schema(defaultValue= "Recommendation not found")))})
    @GetMapping("/{subscriptionId}/{date}")
    public ResponseEntity<RecommendationDTO> getBySubscriptionAndDate (@PathVariable @Positive(message = "Id must be greater than 0") Long subscriptionId, @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate date) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(recommendationService.searchBySubscriptionAndDate(subscriptionId,date));
    }
}
