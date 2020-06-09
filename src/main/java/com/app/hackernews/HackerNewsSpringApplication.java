package com.app.hackernews;

import com.app.hackernews.controller.HackerNewsController;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Pritesh Soni
 *
 */
@RestController
@EnableCaching
@ComponentScan(basePackages= {"com.app.hackernews.*"})
@SpringBootApplication
public class HackerNewsSpringApplication {

	private Logger logger = Logger.getLogger(HackerNewsSpringApplication.class);

	public static void main(String[] args) {
		Logger logger = Logger.getLogger(HackerNewsSpringApplication.class);
		SpringApplication.run(HackerNewsSpringApplication.class, args);
		new HackerNewsController().evictAllCacheValues();
		logger.info("Hacker-News: Cache cleared!");
	}

}
