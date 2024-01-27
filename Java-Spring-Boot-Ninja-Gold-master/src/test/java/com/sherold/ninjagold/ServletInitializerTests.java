package com.sherold.ninjagold;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.springframework.boot.builder.SpringApplicationBuilder;

public class ServletInitializerTests {
	@Test
	public void testConfigure() {
		ServletInitializer servletInitializer = new ServletInitializer();
		SpringApplicationBuilder applicationBuilder = new SpringApplicationBuilder();
		SpringApplicationBuilder configuredBuilder = servletInitializer.configure(applicationBuilder);
	}

    
}