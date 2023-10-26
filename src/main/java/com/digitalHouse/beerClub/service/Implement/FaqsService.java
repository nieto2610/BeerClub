package com.digitalHouse.beerClub.service.Implement;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.Faqs;
import com.digitalHouse.beerClub.model.dto.FaqsDTO;
import com.digitalHouse.beerClub.repository.IFaqsRepository;
import com.digitalHouse.beerClub.service.interfaces.IFaqsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FaqsService implements IFaqsService {

    private final IFaqsRepository faqsRepository;
    private final Mapper mapper;

    public FaqsService(IFaqsRepository faqsRepository, Mapper mapper) {
        this.faqsRepository = faqsRepository;
        this.mapper = mapper;
    }

    @Override
    public List<FaqsDTO> searchAll() {
        return faqsRepository.findAll()
                .stream().map(s -> mapper.converter(s, FaqsDTO.class)).collect(Collectors.toList());
    }

    @Override
    public FaqsDTO searchById(Long id) throws NotFoundException {
        return faqsRepository.findById(id)
                .stream().map(s -> mapper.converter(s, FaqsDTO.class)).findFirst()
                .orElseThrow(() -> new NotFoundException("Faq not found"));
    }

    @Override
    public FaqsDTO create(FaqsDTO faqsDTO) throws BadRequestException {
        Faqs faqs= faqsRepository.save(mapper.converter(faqsDTO, Faqs.class));
        return mapper.converter(faqs, FaqsDTO.class);
    }

       @Override
    public FaqsDTO update(FaqsDTO faqsDTO, Long id) throws NotFoundException {
        Faqs faqs = faqsRepository.findById(id).orElseThrow(() -> new NotFoundException("Faq not found"));
        Faqs faqsA=mapper.converter(faqsDTO,Faqs.class);
        faqsRepository.save(faqsA);
        return mapper.converter(faqs, FaqsDTO.class);
    }
    @Override
    public void delete(Long id) throws ServiceException, NotFoundException {
        Faqs faqs = faqsRepository.findById(id).orElseThrow(() -> new NotFoundException("Faq not found"));
        faqsRepository.delete(faqs);
    }
}