package com.seanrogandev.bebelink.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableR2dbcAuditing
@EnableScheduling
@SpringBootApplication
/**
 * The URL Generator Service is responsible for handling generation of shortened URLs and confirming they exist for other services.
 */
public class UrlGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlGeneratorApplication.class, args);
	}

}
