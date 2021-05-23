package com.microservicio.usuarios.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.microservicio.commons.entity.model.Usuario;

@RepositoryRestResource(path = "usuarios")
public interface IUsuarioRepository extends JpaRepository<Usuario, Long> {
	
	@RestResource(path="buscar-usuario")
	public Usuario findByUsername(@Param("username") String username);
	
	
	@Query("select u from Usuario u where u.username=?1")
	public Usuario obtenerPorUsername(String usuername);
}



