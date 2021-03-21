package com.microservicio.serviciocliente.model.services;

import java.util.List;

import com.microservicio.serviciocliente.model.Grupo;
import com.microservicio.serviciocliente.model.Persona;

public interface ClienteServicio {
	public List<Grupo> findAll();
	public Grupo findById(Long id, String nmGrupo);
	
	public Persona crear(Persona p);
	public Persona editar(Persona p, Long id);
	public void eliminar(Long id);
	
}
