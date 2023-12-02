package com.digitalHouse.beerClub.utils;

import com.digitalHouse.beerClub.exceptions.NotFoundException;
import com.digitalHouse.beerClub.service.implement.PaymentServiceImplement;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class PaymentGeneratorWorker {

    private final PaymentServiceImplement paymentServiceImplement;

    public PaymentGeneratorWorker(PaymentServiceImplement paymentServiceImplement) {
        this.paymentServiceImplement = paymentServiceImplement;
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void worker() throws NotFoundException {

        if (LocalDate.now().getDayOfMonth() == 1) {
            System.out.println("WORKER: Generación de facturas pendientes de pago a usuarios activos - " + LocalDateTime.now());
            //TODO: agregar método que genera pagos masivos a usuarios activos.
            paymentServiceImplement.createPaymentsAndSendInvoices();
        }
    }
}
