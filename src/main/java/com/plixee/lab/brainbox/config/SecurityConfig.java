package com.plixee.lab.brainbox.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.filter.DelegatingFilterProxy;

@Configuration
@ImportResource("classpath:security.xml")
public class SecurityConfig {
	@Bean
	public DelegatingFilterProxy springSecurityFilterChain() {
		return new DelegatingFilterProxy();
	}
}
