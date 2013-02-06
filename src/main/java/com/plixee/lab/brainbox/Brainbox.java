package com.plixee.lab.brainbox;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import com.plixee.lab.brainbox.config.WebConfig;

public class Brainbox {
	public static void main(String[] args) throws Exception {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		applicationContext.register(WebConfig.class);

		ServletHolder servletHolder = new ServletHolder("brainbox",
				new DispatcherServlet(applicationContext));
		ServletContextHandler servletContext = new ServletContextHandler();
		servletContext.setContextPath("/");
		servletContext.addServlet(servletHolder, "/*");

		Server server = new Server(9999);

		server.setHandler(servletContext);

		server.start();
		server.join();
	}
}