package com.microservicio.serviciocliente.model.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.microservicio.serviciocliente.clienteHttpFeing.IClienteFeing;
import com.microservicio.serviciocliente.model.Grupo;
import com.microservicio.serviciocliente.model.Persona;

@Service("servicioFeing")

public class ClienteServicioFeingImpl implements ClienteServicio {
	
	@Autowired
	IClienteFeing clienteFeing;

	@Override
	public List<Grupo> findAll() {		
		return clienteFeing.list().getBody().stream().map(p -> new Grupo(p, "Grupo B")).collect(Collectors.toList());
	}

	@Override
	public Grupo findById(Long id, String nmGrupo) {
		return new Grupo(clienteFeing.load(id).getBody(), nmGrupo);
	}

	@Override
	public Persona crear(Persona p) {		
		return clienteFeing.insert(p).getBody();
	}

	@Override
	public Persona editar(Persona p, Long id) {
		return clienteFeing.update(p, id).getBody();
	}

	@Override
	public void eliminar(Long id) {
		clienteFeing.delete(id);
		
	}

	

}
