package com.plixee.lab.brainbox;

import java.util.EnumSet;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import com.plixee.lab.brainbox.config.WebConfig;

public class Brainbox {
	public static void main(String[] args) throws Exception {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(WebConfig.class);
		applicationContext.setDisplayName("Brainbox");

		ServletHolder servletHolder = new ServletHolder("brainbox",
				new DispatcherServlet(applicationContext));

		ServletContextHandler servletContext = new ServletContextHandler(null,
				"/", true, false);
		servletContext.addEventListener(new ContextLoaderListener(
				applicationContext));
		// servletContext.setContextPath("/");
		servletContext.addServlet(servletHolder, "/*");

		// Spring Security
		FilterHolder filterHolder = new FilterHolder(
				DelegatingFilterProxy.class);
		filterHolder.setName("springSecurityFilterChain");

		servletContext.addFilter(filterHolder, "/*",
				EnumSet.allOf(DispatcherType.class));

		// Server
		Server server = new Server(9999);
		server.setHandler(servletContext);
		server.start();
		server.join();
	}
}