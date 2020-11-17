package com.microservicio.serviciocliente.clienteHttpFeing;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservicio.serviciocliente.model.Persona;

@FeignClient(name = "servicio-personas")
public interface IClienteFeing {
	
	@GetMapping("/listar")
    public ResponseEntity<List<Persona>> list();
    
	@GetMapping("/buscar/{id}")
    public ResponseEntity<Persona> load(@PathVariable("id") Long id);

}
