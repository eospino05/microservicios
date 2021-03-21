package com.microservicio.serviciocliente.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.serviciocliente.model.Grupo;
import com.microservicio.serviciocliente.model.Persona;
import com.microservicio.serviciocliente.model.services.ClienteServicio;

@RefreshScope
@RestController
//@RequestMapping("/api/cliente/personas")

public class ClienteController {

	@Autowired
	// @Qualifier("servicioRestTemplate")
	@Qualifier("servicioFeing")
	ClienteServicio clienteServicio;

	@Autowired
	private Environment env;

	@Value("${configuracion.texto}")
	private String texto;

	
	@SuppressWarnings("rawtypes")
	@GetMapping("/listar")
	public ResponseEntity<?> list() {

		try {
			return new ResponseEntity<List<Grupo>>(clienteServicio.findAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// @HystrixCommand(fallbackMethod = "buscarAlterno")
	@SuppressWarnings("rawtypes")
	@GetMapping("/buscar/{id}/grupo/{grupo}")
	public ResponseEntity<?> load(@PathVariable("id") Long id, @PathVariable("grupo") String grupo) {

		try {
			return new ResponseEntity<Grupo>(clienteServicio.findById(id, grupo), HttpStatus.OK);

		} catch (Exception n) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	public ResponseEntity<?> buscarAlterno(Long id, String grupo) {
		Persona p = new Persona();
		p.setId(id);
		p.setNombre("Pepito");
		p.setIdentificacion("123322");
		p.setApellidos("Perez Prieto");

		Grupo g = new Grupo(p, grupo);

		return new ResponseEntity<Grupo>(g, HttpStatus.OK);
	}

	@GetMapping("/obtener-config")
	public ResponseEntity<?> load(@Value("${server.port}") String puerto) {

		Map<String, String> json = new HashMap<String, String>();
		json.put("texto", texto);
		json.put("puerto", puerto);

		if (env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
			json.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
			json.put("autor.email", env.getProperty("configuracion.autor.email"));
		}

		return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);

	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/crear")
	public ResponseEntity<?> insert(@RequestBody Persona persona) {

		try {

			return new ResponseEntity<Persona>(clienteServicio.crear(persona), HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("/editar/{id}")
	public ResponseEntity<?> update(@RequestBody Persona persona, @PathVariable("id") Long id) {

		try {

			return new ResponseEntity<Persona>(clienteServicio.editar(persona, id), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@SuppressWarnings("rawtypes")
	@DeleteMapping("eliminar/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {

		try {
			clienteServicio.eliminar(id);
			return new ResponseEntity(HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
