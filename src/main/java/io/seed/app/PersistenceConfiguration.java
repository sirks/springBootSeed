package io.seed.app;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@EnableTransactionManagement
@Configuration
class PersistenceConfiguration {

	@Bean(destroyMethod = "close")
	public DataSource dataSource(
			@Value("${db.host}") String host,
			@Value("${db.port:5432}") int port,
			@Value("${db.dbname}") String dbName,
			@Value("${db.user}") String user,
			@Value("${db.password}") String password,
			@Value("${db.dataSourceClass:org.postgresql.ds.PGSimpleDataSource}") String dataSourceClassName,
			@Value("${db.maximumPoolSize:10}") int maxPoolSize,
			@Value("${db.idleTimeoutSec:60}") int idleTimeoutSec
	) throws PropertyVetoException {
		HikariConfig config = new HikariConfig();
		config.setMaximumPoolSize(maxPoolSize);
		config.setDataSourceClassName(dataSourceClassName);
		config.addDataSourceProperty("serverName", host);
		config.addDataSourceProperty("portNumber", port);
		config.addDataSourceProperty("databaseName", dbName);
		config.addDataSourceProperty("user", user);
		config.addDataSourceProperty("password", password);
		config.setIdleTimeout(idleTimeoutSec * 1000);
		config.setAutoCommit(false);
		return new HikariDataSource(config);
	}

	@Bean
	public Properties hibernateProperties(
			@Value("${hibernate.dialect}") String dialect,
			@Value("${hibernate.hbm2ddl.auto}") String hbm2ddl,
			@Value("${hibernate.show_sql}") boolean showSql,
			@Value("${db.fetch:0}") int fetchSize
	) {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", dialect);
		properties.put("hibernate.show_sql", showSql);

		properties.put("hibernate.hbm2ddl.auto", hbm2ddl);
		properties.put("hibernate.jdbc.fetch_size", fetchSize);
		properties.put("hibernate.order_updates", true);
//		properties.put("hibernate.cache.use_second_level_cache", true);
//		properties.put("hibernate.cache.use_query_cache", true);
//		properties.put("hibernate.cache.region.factory_class", SingletonEhCacheRegionFactory.class.getName());
		properties.put("org.hibernate.envers.audit_table_prefix", "AUDIT_");
		properties.put("org.hibernate.envers.audit_table_suffix", "");

//		properties.put("jadira.usertype.autoRegisterUserTypes", "true");
//		properties.put("jadira.usertype.javaZone", "jvm");
//		properties.put("jadira.usertype.databaseZone", "jvm");

		return properties;
	}

	@Bean
	public SessionFactory sessionFactory(DataSource dataSource, @Qualifier("hibernateProperties") Properties properties) throws Exception {

		LocalSessionFactoryBean sessionFactoryBean = new LocalSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
		sessionFactoryBean.setPackagesToScan("io.seed.entities");
		sessionFactoryBean.setHibernateProperties(properties);
		sessionFactoryBean.afterPropertiesSet();
		SessionFactory sessionFactory = sessionFactoryBean.getObject();
		return sessionFactory;
	}

	@Bean
	public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
		return new HibernateTransactionManager(sessionFactory);
	}
}
