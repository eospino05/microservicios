package com.microservicio.serviciocliente.clienteHttpFeing;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.microservicio.serviciocliente.model.Persona;

@FeignClient(name = "servicio-personas", url = "http://localhost:8081/api/v1/personas")
public interface IClienteFeing {
	
	@GetMapping
    public ResponseEntity<List<Persona>> list();
    
	@GetMapping("/{id}")
    public ResponseEntity<Persona> load(@PathVariable("id") Long id);

}
