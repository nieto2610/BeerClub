package com.digitalHouse.beerClub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableJpaRepositories
public class BeerClubApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeerClubApplication.class, args);
	}
}
