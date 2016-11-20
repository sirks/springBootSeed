package io.seed.app;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;

@Configuration
public class JacksonConfig {

	@Bean
	public ObjectMapper setObjectMapper() {
		SimpleModule module = new SimpleModule();
		module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(ISO_LOCAL_DATE_TIME));
		module.addDeserializer(LocalDate.class, new LocalDateDeserializer(ISO_LOCAL_DATE));
		module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(ISO_LOCAL_DATE_TIME));
		module.addSerializer(LocalDate.class, new LocalDateSerializer(ISO_LOCAL_DATE));
		
		ObjectMapper mapper = new ObjectMapper()
				.registerModule(module)
				.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper;
	}
}
