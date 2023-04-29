package com.vega.demo;

import com.vega.demo.domain.Comment;
import com.vega.demo.domain.Link;
import com.vega.demo.repository.CommentRepository;
import com.vega.demo.repository.LinkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Arrays;

@SpringBootApplication
@EnableJpaAuditing
public class SpringitApplication {
	@Autowired
	private ApplicationContext applicationContext;

	private static final Logger log = LoggerFactory.getLogger(SpringitApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(SpringitApplication.class, args);

	}

	@Bean
	@Profile("dev")
	CommandLineRunner runner(LinkRepository linkRepository, CommentRepository commentRepository){
		return args -> {
			Link link = new Link("Getting Started with SP", "http://springboot.com");
			linkRepository.save(link);

			Link link2 = new Link("Getting Started with Haircutting", "http://cutmyhair.com");
			linkRepository.save(link2);

			Comment comment = new Comment("I am a fast learner", link);
			commentRepository.save(comment);

			Comment comment2 = new Comment("I am still a fast learner", link);
			commentRepository.save(comment2);

			System.out.println("Data Insert Completed");

			// Everything after this can be deleted

			Link newLink = linkRepository.findByTitle("Getting Started with SP");
			System.out.println(newLink.getUrl());

			System.out.println("List of Beans:");
			String[] beans = applicationContext.getBeanDefinitionNames();
			Arrays.sort(beans);
			//for(String bean: beans) {
			//	System.out.println(bean);
			//}
			log.error("Error logged");
			log.debug("Debug Logged");
			log.info("Info Logged");
			log.warn("Warning Logged");

		};

	}

}
