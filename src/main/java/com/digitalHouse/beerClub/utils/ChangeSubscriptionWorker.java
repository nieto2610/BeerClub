package com.digitalHouse.beerClub.utils;

import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.service.implement.UserServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.logging.Logger;

@Component
public class ChangeSubscriptionWorker {


    @Autowired
    UserServiceImplement userService;

    @Scheduled(cron = "0 * * * * ?")
    public void worker() throws NotFoundException {

        if (LocalDate.now().getDayOfMonth() == 1) {
            System.out.println("WORKER: actualización cambio de suscripción - " + LocalDateTime.now());
            userService.updateUserSubscriptionWorker();
        }
    }
}
