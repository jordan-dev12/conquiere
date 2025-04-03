package com.tbass.conquier.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info().title("Conquiere API").version("1.0").description("Documentation API Conquiere")
						.contact(new Contact().name("Jordan").email("jordan.kework3@gmail.com")));
	}

}
