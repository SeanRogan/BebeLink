package com.seanrogandev.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
	/**
	 * Starts the Eureka discovery server.
	 * <p>the discovery server handles service discovery and acts as the service registry for microservices.</p>
	 * @param args
	 */

	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
	}

}
