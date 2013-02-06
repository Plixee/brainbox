package com.plixee.lab.brainbox.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

@Configuration
@ComponentScan(basePackages = { "com.plixee.lab.brainbox.controller" })
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {
	@Bean
	public FreeMarkerViewResolver viewResolver() {
		FreeMarkerViewResolver viewResolver = new FreeMarkerViewResolver();
		viewResolver.setCache(false);
		viewResolver.setPrefix("");
		viewResolver.setSuffix(".ftl");
		viewResolver.setContentType("text/html;charset=UTF-8");
		return viewResolver;
	}

	@Bean
	public FreeMarkerConfigurer freeMarkerConfigurer() {
		FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
		configurer.setTemplateLoaderPath("classpath:/templates/");
		configurer.setDefaultEncoding("UTF-8");
		return configurer;
	}
}
