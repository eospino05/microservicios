package com.microservicio.serviciocliente.clienteHttpFeing;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.microservicio.commons.entity.model.Persona;

@FeignClient(name = "servicio-personas")
public interface IClienteFeing {
	
	@GetMapping("/listar")
    public ResponseEntity<List<Persona>> list();
    
	@GetMapping("/buscar/{id}")
    public ResponseEntity<Persona> load(@PathVariable("id") Long id);
	
	@PostMapping("/crear")
    public ResponseEntity<Persona>  insert(@RequestBody Persona persona);
	
	@PutMapping("/editar/{id}")
    public ResponseEntity<Persona>  update(@RequestBody Persona persona, @PathVariable("id") Long id);
	
	@DeleteMapping("eliminar/{id}")
    public ResponseEntity<Persona>  delete(@PathVariable("id") Long id);

}
