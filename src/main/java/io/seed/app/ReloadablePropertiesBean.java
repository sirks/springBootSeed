package io.seed.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Properties;

import static io.seed.app.PropertiesConfiguration.PROPERTY_FILE;

@Component
@ManagedResource(objectName = "io.seed:name=PropertiesMbean")
public class ReloadablePropertiesBean {

	private static final Logger log = LoggerFactory.getLogger(ReloadablePropertiesBean.class);

	private Properties properties = new Properties();

	@PostConstruct
	public void init() throws IOException {
		readProperties();
	}

	private void readProperties() {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(PROPERTY_FILE);
		try {
			propertiesFactoryBean.afterPropertiesSet();
			properties = propertiesFactoryBean.getObject();
		} catch (IOException e) {
			log.error("cannot read properties", e);
		}
	}

	@ManagedOperation
	public void reload() {
		readProperties();
	}

	@ManagedOperation
	public String getProperty(String name) {
		return properties.getProperty(name);
	}
}
