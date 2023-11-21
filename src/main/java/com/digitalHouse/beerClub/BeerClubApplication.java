package com.digitalHouse.beerClub;

import com.digitalHouse.beerClub.repository.IUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class BeerClubApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeerClubApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(IUserRepository userRepository) {
		return (args) -> {};}
}
