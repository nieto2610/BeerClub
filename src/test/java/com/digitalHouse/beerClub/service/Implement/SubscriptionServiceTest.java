package com.digitalHouse.beerClub.service.Implement;

import com.digitalHouse.beerClub.repository.ISubscriptionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith((MockitoExtension.class))
class SubscriptionServiceTest {

    @Mock
    ISubscriptionRepository subscriptionRepository;


    @Test
    @DisplayName("Search all the subscriptions")
    void searchAll() {
        //ARRANGE
        //ACT
        //ASSERT
    }

    @Test
    void searchById() {
    }

    @Test
    void create() {
    }

    @Test
    void delete() {
    }
}