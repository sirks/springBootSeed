package io.seed.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

@Configuration
class PropertiesConfiguration {

	public static final ClassPathResource PROPERTY_FILE = new ClassPathResource("app.properties");

	@Bean
    public static PropertySourcesPlaceholderConfigurer propsConfiguration() {

        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(PROPERTY_FILE);

        configurer.setIgnoreResourceNotFound(false);

        return configurer;
    }
}
