package com.microservicio.serviciopersonas.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.microservicio.commons.entity.model.Persona;

@Repository
public interface IPersonaRepository extends JpaRepository<Persona, Long> {
}
