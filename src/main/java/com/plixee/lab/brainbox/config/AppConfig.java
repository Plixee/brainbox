package com.plixee.lab.brainbox.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.plixee.lab.brainbox.repository.IdeaRepository;
import com.plixee.lab.brainbox.repository.UserRepository;
import com.plixee.lab.brainbox.service.IdeaService;
import com.plixee.lab.brainbox.service.LoginService;
import com.plixee.lab.brainbox.service.PopulateService;
import com.plixee.lab.brainbox.service.UserService;

@Configuration
@ComponentScan(basePackageClasses = { IdeaService.class, LoginService.class,
		PopulateService.class, UserService.class })
@EnableJpaRepositories(basePackageClasses = { IdeaRepository.class,
		UserRepository.class })
@EnableTransactionManagement
public class AppConfig {
	@Bean
	public DataSource dataSource() {
		EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
		return builder.setType(EmbeddedDatabaseType.HSQL).build();
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setDatabase(Database.HSQL);
		vendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.plixee.lab.brainbox.model");
		factory.setDataSource(dataSource());
		factory.afterPropertiesSet();

		return factory.getObject();
	}

	@Bean
	public PersistenceExceptionTranslator exceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory());
		return txManager;
	}
}
