package com.microservicio.serviciopersonas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class ServicioPersonasApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioPersonasApplication.class, args);
	}

}
