package com.tbass.conquier.config;

import java.time.format.DateTimeFormatter;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

@Configuration
public class JacksonConfig {

	private static final String FORMATTER = "dd-MM-yyyy";

	@Bean
	protected Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
		return builder -> {
			builder.simpleDateFormat(FORMATTER);
			builder.serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(FORMATTER)));
			builder.deserializers(new LocalDateDeserializer(DateTimeFormatter.ofPattern(FORMATTER)));
		};
	}

}
