package com.microservicio.serviciocliente.model.services;

import java.util.List;

import com.microservicio.serviciocliente.model.Grupo;

public interface ClienteServicio {
	public List<Grupo> findAll();
	public Grupo findById(Long id, String nmGrupo);
}
