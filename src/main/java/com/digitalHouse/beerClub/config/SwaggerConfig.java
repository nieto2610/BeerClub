package com.digitalHouse.beerClub.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Info info = new Info()
                .title("Beer Club API Documentation")
                .version("1.0.0")
                .description("Beer Club API Documentation")
                .contact(new Contact()
                        .name("Beer Club Api")
                        .email("beer.club.1023@gmail.com"));

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }

}
