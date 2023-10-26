package com.digitalHouse.beerClub.controller;
import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.model.dto.FaqsDTO;
import com.digitalHouse.beerClub.service.Implement.FaqsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.logging.Logger;

@Validated
@RestController
@RequestMapping("faqs")
public class FaqsController {
    final static Logger logger = Logger.getLogger(String.valueOf(FaqsController.class));
    private final FaqsService faqsService;

    public FaqsController(FaqsService faqsService) {
        this.faqsService = faqsService;
    }

    @GetMapping
    public List<FaqsDTO> searchAll() {
        logger.info("We are listing frequently asked questions and their answers");
        return faqsService.searchAll();
    }

    @PostMapping
    public FaqsDTO save(@RequestBody FaqsDTO faqsDTO) throws BadRequestException {
        logger.info("We are saving the new FAQ and its answer");
        return faqsService.create(faqsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FaqsDTO> getById(@PathVariable Long id) throws NotFoundException {
        logger.info("We are looking for the asked question which id:" + id+" and its answer");
        return  ResponseEntity.status(HttpStatus.OK).body(faqsService.searchById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<FaqsDTO> update(@RequestBody FaqsDTO faqsDTO, @PathVariable Long id) throws NotFoundException {
        logger.info("We are updating the asked question and its answer");
        return ResponseEntity.status(HttpStatus.OK).body(faqsService.update(faqsDTO,id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<FaqsDTO> delete (@PathVariable Long id) throws NotFoundException, ServiceException {
        logger.info("We are deleting the id FAQ: " + id +" and its answer");
        faqsService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
