package com.microservicio.oauth.services;

import com.microservicio.commons.entity.model.Usuario;

public interface IUsuarioService {

	public Usuario findByUsername(String username);
}
