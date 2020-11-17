package com.microservicio.serviciocliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ServicioClienteApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioClienteApplication.class, args);
	}

}
