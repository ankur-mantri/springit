package com.vega.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.util.Arrays;

@SpringBootApplication
//@EnableConfigurationProperties(SpringitApplication.class)
public class SpringitApplication {
	@Autowired
	private ApplicationContext applicationContext;

	private static final Logger log = LoggerFactory.getLogger(SpringitApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SpringitApplication.class, args);
		System.out.println("Testing BRANCHING and more");

	}

	@Bean
	@Profile("dev")
	CommandLineRunner runner(){
		return args -> {
			System.out.println("List of Beans:");
			String[] beans = applicationContext.getBeanDefinitionNames();
			Arrays.sort(beans);
			for(String bean: beans) {
				System.out.println(bean);
			}
			log.error("Error logged");
			log.debug("Debug Logged");
			log.info("Info Logged");
			log.warn("Warning Logged");

		};

	}

}
