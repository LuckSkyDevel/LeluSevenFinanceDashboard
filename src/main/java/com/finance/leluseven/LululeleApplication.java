package com.finance.leluseven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class LululeleApplication {

	public static void main(String[] args) {
		SpringApplication.run(LululeleApplication.class, args);
	}

}
