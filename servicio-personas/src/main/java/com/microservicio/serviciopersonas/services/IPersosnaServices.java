package com.microservicio.serviciopersonas.services;

import java.util.List;
import java.util.Optional;
import com.microservicio.serviciopersonas.exception.NotFoundException;
import com.microservicio.serviciopersonas.exception.ServicesException;


import com.microservicio.serviciopersonas.model.Persona;
public interface IPersosnaServices {
	public List<Persona> list() throws ServicesException;
    public Optional<Persona> load(Long id) throws ServicesException ;
    public Persona save(Persona persona) throws  ServicesException;
    public void remove(Long id) throws ServicesException, NotFoundException;
}
