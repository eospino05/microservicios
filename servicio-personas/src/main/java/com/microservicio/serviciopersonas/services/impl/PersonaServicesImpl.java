package com.microservicio.serviciopersonas.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservicio.serviciopersonas.dao.IPersonaRepository;
import com.microservicio.serviciopersonas.exception.NotFoundException;
import com.microservicio.serviciopersonas.exception.ServicesException;
import com.microservicio.serviciopersonas.model.Persona;
import com.microservicio.serviciopersonas.services.IPersosnaServices;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaServicesImpl implements IPersosnaServices {

    @Autowired
    IPersonaRepository personaRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Persona> list() throws ServicesException {

        try {
            return personaRepository.findAll();

        }catch (Exception e){
            throw new ServicesException(e.getMessage());
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Persona> load(Long id) throws ServicesException {
        Optional<Persona> op;

        try {
            op = personaRepository.findById(id);

        }catch (Exception e){
            throw new ServicesException(e.getMessage());
        }

       /* if(!op.isPresent){
            throw NotFoundException("No se enconto persona con el id $id")
        }*/

        return  op;
    }

    @Override
    @Transactional
    public Persona save(Persona persona) throws  ServicesException{

        try {
            return  personaRepository.save(persona);

        }catch (Exception e){
            throw new ServicesException(e.getMessage());
        }
    }

    @Override
    public void remove(Long id) throws ServicesException, NotFoundException {

        Optional<Persona> op;

        try {
            op = personaRepository.findById(id);

        }catch (Exception e){
            throw new ServicesException(e.getMessage());
        }

        if(!op.isPresent()){
            throw new NotFoundException(String.format("No se enconto persona con el id %d", id));
        }else{

            try {
                personaRepository.deleteById(id);

            }catch (Exception e){
                throw new ServicesException(e.getMessage());
            }

        }

    }
}
