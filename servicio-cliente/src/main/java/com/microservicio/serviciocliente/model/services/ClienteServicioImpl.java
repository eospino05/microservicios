package com.microservicio.serviciocliente.model.services;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservicio.serviciocliente.model.Grupo;
import com.microservicio.serviciocliente.model.Persona;

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

}
