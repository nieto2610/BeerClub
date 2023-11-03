package com.digitalHouse.beerClub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
<<<<<<< HEAD
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
=======

>>>>>>> development

@SpringBootApplication
public class BeerClubApplication {

<<<<<<< HEAD
	public static void main(String[] args) {
		SpringApplication.run(BeerClubApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*")
						.allowedMethods("*")
						.allowedHeaders("*");
			}
		};
	}
=========
    public static void main(String[] args) {

        SpringApplication.run(BeerClubApplication.class, args);
    }

>>>>>>>>> Temporary merge branch 2
}
