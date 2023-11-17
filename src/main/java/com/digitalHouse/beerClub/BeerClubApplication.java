package com.digitalHouse.beerClub;

import com.digitalHouse.beerClub.model.RoleType;
import com.digitalHouse.beerClub.model.User;
import com.digitalHouse.beerClub.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
@EnableJpaRepositories
public class BeerClubApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeerClubApplication.class, args);
	}
	/*
	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	public CommandLineRunner initData(IUserRepository userRepository) {
		return (args) -> {

			User admin = new User("admin", "admin", "admin@beerclub.com", passwordEncoder.encode("Admin123#"),true);
			admin.assignRole(RoleType.ADMIN);

			userRepository.save(admin);

		};
	}
	*/
}
