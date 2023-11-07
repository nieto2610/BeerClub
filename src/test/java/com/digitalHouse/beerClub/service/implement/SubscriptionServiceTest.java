package com.digitalHouse.beerClub.service.implement;

import com.digitalHouse.beerClub.exceptions.BadRequestException;
import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.exceptions.ServiceException;
import com.digitalHouse.beerClub.mapper.Mapper;
import com.digitalHouse.beerClub.model.Benefit;
import com.digitalHouse.beerClub.model.Subscription;
import com.digitalHouse.beerClub.model.dto.BenefitDTO;
import com.digitalHouse.beerClub.model.dto.SubscriptionDTO;
import com.digitalHouse.beerClub.repository.IBenefitRepository;
import com.digitalHouse.beerClub.repository.ISubscriptionRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest {

    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @Mock
    private IBenefitRepository benefitRepository;

    @Mock
    private Mapper mapper;

    private SubscriptionService subscriptionService;

    @BeforeEach
    void setUp() {
        mapper = new Mapper(new ModelMapper());
        subscriptionService = new SubscriptionService(subscriptionRepository, benefitRepository, mapper);
    }

    @Test
    @DisplayName("✅ - Search all the subscriptions")
    void searchAll() {
        //ARRANGE
        List<Subscription> expected = new ArrayList<>();
        expected.add(new Subscription(1L, "Novato", "Disfrutas de la cerveza y quieres conocer más acerca de ella", 200.0, List.of(), false, true));
        expected.add(new Subscription(2L, "Experto", "Disfrutas de la cerveza y quieres conocer más acerca de ella", 300.0, List.of(), true, true));
        when(subscriptionRepository.findAll()).thenReturn(expected);

        //ACT
        List<SubscriptionDTO> result = subscriptionService.searchAll();

        //ASSERT
        Assertions.assertEquals(expected.size(), result.size());
    }

    @Test
    @DisplayName("✅ - Search subscriptions by Id")
    void searchById() throws NotFoundException {
        //ARRANGE
        Long id = 1L;
        Subscription subscription = new Subscription(1L, "Novato", "Disfrutas de la cerveza y quieres conocer más acerca de ella", 200.0, List.of(), false, true);
        when(subscriptionRepository.findById(any())).thenReturn(Optional.of(subscription));

        //ACT
        SubscriptionDTO result = subscriptionService.searchById(id);

        //ASSERT
        Assertions.assertEquals(id, result.getId());
    }

    @Test
    @DisplayName("❌ Not Found- Search subscriptions by Id")
    void searchByIdNotFound() throws NotFoundException {
        //ARRANGE
        Long id = 1L;
        when(subscriptionRepository.findById(1L)).thenReturn(Optional.ofNullable(null));

        //ACT&ASSERT
        Assertions.assertThrows(NotFoundException.class, () -> subscriptionService.searchById(id));
    }

    @Test
    @DisplayName("✅ - Create subscription")
    void create() throws BadRequestException {
        //ARRANGE
        SubscriptionDTO expected = new SubscriptionDTO(1L, "Novato", "Disfrutas de la cerveza y quieres conocer más acerca de ella", 200.0, List.of(), false, true);
        Subscription subscription = new Subscription(1L, "Novato", "Disfrutas de la cerveza y quieres conocer más acerca de ella", 200.0, List.of(), false, true);
        when(subscriptionRepository.save(subscription)).thenReturn(subscription);

        //ACT
        SubscriptionDTO result = subscriptionService.create(expected);

        //ASSERT
        Assertions.assertEquals(expected, result);
        
    }

    @Test
    @Disabled
    @DisplayName("✅ - Update subscription")
    void update() throws NotFoundException {
        //ARRANGE
        Long id = 1L;
        List<Benefit> benefits = new ArrayList<>();
        benefits.add(new Benefit(1L,"descuentos del 5%"));

        List<BenefitDTO> benefitsDTO = new ArrayList<>();
        benefitsDTO.add(new BenefitDTO(1L,"descuentos del 10%"));

        SubscriptionDTO subscriptionDTO = new SubscriptionDTO(1L, "Novato-up", "Disfrutas de la cerveza y quieres conocer más acerca de ella", 200.0, benefitsDTO, false, true);

        Subscription subscription = new Subscription(1L, "Novato", "Disfrutas de la cerveza y quieres conocer más acerca de ella", 100.0, benefits, false, true);
        when(subscriptionRepository.findById(any())).thenReturn(Optional.of(subscription));
        //ACT
        SubscriptionDTO result = subscriptionService.update(subscriptionDTO,id);
        //ASERT
        Assertions.assertEquals(subscriptionDTO.getName(),result.getName());
    }

    @Test
    @DisplayName("✅ - Soft Delete by Id")
    void delete() throws ServiceException, NotFoundException {
        //ARRANGE
        Long id = 1L;
        Subscription subscription = new Subscription(1L, "Novato", "Disfrutas de la cerveza y quieres conocer más acerca de ella", 200.0, List.of(), false, true);
        when(subscriptionRepository.findById(any())).thenReturn(Optional.of(subscription));

        //ACT
        subscriptionService.delete(id);

        //ASSERT
        verify(subscriptionRepository, atLeastOnce()).findById(id);
    }
}