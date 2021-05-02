package com.microservicio.oauth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.microservicio.commons.entity.model.Usuario;

@FeignClient(name = "servicio-usuarios")
public interface IUsuarioFeignClient {
	
	@GetMapping("/usuarios/search/buscar-usuario")
	public Usuario findByUsername(@RequestParam String username);

}
