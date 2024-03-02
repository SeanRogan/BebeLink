package com.seanrogandev.bebelink.router;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RouterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RouterServiceApplication.class, args);
	}

}
