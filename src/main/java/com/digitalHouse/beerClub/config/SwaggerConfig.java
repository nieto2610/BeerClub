package com.digitalHouse.beerClub.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Beer Club API",
                version = "1.4.2",
                description = "Aplicación desarrollada para el Proyecto Integrador II de la carrera Certified Tech Developer de Digital House.\n\nGrupo 4(" +
                        "- Belén Santochi" +
                        " - Camilo Martinez" +
                        " - Carla Anahí Nieto" +
                        " - Clara Lisle" +
                        " - Delfina Molter" +
                        " - Juan Camargo" +
                        " - Laura Lopez" +
                        " - Mauricio Grisales" +
                        " - Paola Gómez" +
                        " - Winston Shaw).",
                contact = @Contact(
                        name = "Beer Club",
                        email = "beer.club.1023@gmail.com",
                        url = "http://localhost:8080/api/v1/"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://springdoc.org"
                )
        )
)
    public class SwaggerConfig {
        @Bean
        public OpenAPI customOpenAPI() {
            SecurityScheme securityScheme = new SecurityScheme()
                    .type(SecurityScheme.Type.HTTP)
                    .scheme("bearer")
                    .bearerFormat("JWT")
                    .in(SecurityScheme.In.HEADER)
                    .name("Authorization");

            SecurityRequirement securityItem = new SecurityRequirement().addList("bearerAuth");

            return new OpenAPI()
                    .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                    .addSecurityItem(securityItem);
        }
}
