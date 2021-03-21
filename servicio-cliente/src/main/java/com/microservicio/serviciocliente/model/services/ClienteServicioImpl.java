package com.microservicio.serviciocliente.model.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservicio.serviciocliente.model.Grupo;
import com.microservicio.serviciocliente.model.Persona;

import io.micrometer.core.ipc.http.HttpSender.Method;

@Service("servicioRestTemplate")
public class ClienteServicioImpl implements ClienteServicio {

	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public List<Grupo> findAll() {
		List<Persona> list = Arrays.asList(restTemplate.getForObject("http://servicio-personas/listar", Persona[].class));
		
		return list.stream().map(p -> new Grupo(p, "Grupo A")).collect(Collectors.toList());
	}

	@Override
	public Grupo findById(Long id, String nmGrupo) {
		
		Map<String, String> pathVariable = new HashMap<String, String>();
		pathVariable.put("id", id.toString());
		
		Persona persona = restTemplate.getForObject("http://servicio-personas/buscar/{id}", Persona.class, pathVariable);
		
		return  new Grupo(persona, nmGrupo);
	}

	@Override
	public Persona crear(Persona p) {
		
		HttpEntity<Persona> body = new HttpEntity<Persona>(p);
		
		ResponseEntity<Persona>  response =  restTemplate.exchange("http://servicio-personas/crear",HttpMethod.POST, body,  Persona.class);
		
		
		return response.getBody();
	}

	@Override
	public Persona editar(Persona p, Long id) {
		
		HttpEntity<Persona> body = new HttpEntity<Persona>(p);
		
		Map<String, String> pathVariable = new HashMap<String, String>();
		pathVariable.put("id", id.toString());
		
		ResponseEntity<Persona>  response =  restTemplate.exchange("http://servicio-personas/editar/{id}",HttpMethod.PUT, body, Persona.class, pathVariable);
		
		
		return response.getBody();
	}

	@Override
	public void eliminar(Long id) {
		
		Map<String, String> pathVariable = new HashMap<String, String>();
		pathVariable.put("id", id.toString());
		
		restTemplate.delete("http://servicio-personas/eliminar/{id}", pathVariable);
		
	}

}
