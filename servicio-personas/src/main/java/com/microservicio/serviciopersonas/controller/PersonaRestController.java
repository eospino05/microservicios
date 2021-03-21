package com.microservicio.serviciopersonas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.microservicio.serviciopersonas.exception.NotFoundException;
import com.microservicio.commons.entity.model.Persona;
import com.microservicio.serviciopersonas.services.IPersosnaServices;

@RestController
//@RequestMapping(Constans.URL_BASE_PERSONAS)
public class PersonaRestController {

	@Autowired
	IPersosnaServices persosnaServices;

	@Value("${server.port}")
	private Integer puerto;

	@SuppressWarnings("rawtypes")
	@GetMapping("/listar")
	public ResponseEntity<?> list() {

		try {

			return new ResponseEntity<List<Persona>>(persosnaServices.list().stream().map(p -> {
				p.setPuerto(puerto);
				return p;
			}).collect(Collectors.toList()), HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@SuppressWarnings("rawtypes")
	@GetMapping("/buscar/{id}")
	public ResponseEntity<?> buscar(@PathVariable("id") Long id) throws Exception {
		Optional<Persona> op;
		/*
		 * Boolean b = true; if(b) throw new Exception("Servisio no disponible");
		 */

		// Thread.sleep(2000L);

		try {

			op = persosnaServices.load(id);

			if (op.isPresent()) {
				Persona p = op.get();
				p.setPuerto(puerto);
				return new ResponseEntity<Persona>(p, HttpStatus.OK);
			} else {
				return new ResponseEntity(HttpStatus.NOT_FOUND);
			}

		} catch (Exception n) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

		}
	}

	@SuppressWarnings("rawtypes")
	@PostMapping("/crear")
	public ResponseEntity<?> insert(@RequestBody Persona persona) {

		try {

			// HttpHeaders responseHeader = new HttpHeaders();
			// responseHeader.set("idNuevaPersona", Constans.URL_BASE_PERSONAS + "/" +
			// persona.getId());
			return new ResponseEntity<Persona>(persosnaServices.save(persona), HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@SuppressWarnings("rawtypes")
	@PutMapping("/editar/{id}")
	public ResponseEntity<?> update(@RequestBody Persona persona, @PathVariable("id") Long id) {

		try {
			Optional<Persona> op = persosnaServices.load(id);

			if (op.isPresent()) {

				Persona p = op.get();
				p.setNombre(persona.getNombre());
				p.setApellidos(persona.getApellidos());
				p.setFechaNacimiento(persona.getFechaNacimiento());

				return new ResponseEntity<Persona>(persosnaServices.save(p), HttpStatus.OK);

			} else {

				return new ResponseEntity(HttpStatus.NOT_FOUND);

			}

		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@SuppressWarnings("rawtypes")
	@DeleteMapping("eliminar/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {

		try {
			persosnaServices.remove(id);
			return new ResponseEntity(HttpStatus.OK);

		} catch (NotFoundException n) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
