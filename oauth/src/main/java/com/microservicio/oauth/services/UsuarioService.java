package com.microservicio.oauth.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.microservicio.commons.entity.model.Usuario;
import com.microservicio.oauth.client.IUsuarioFeignClient;

@Service
public class UsuarioService implements UserDetailsService, IUsuarioService{

	private Logger log = LoggerFactory.getLogger(UsuarioService.class);
	@Autowired
	private IUsuarioFeignClient client;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		log.info("Inicio loadUserByUsername___________________________" + username);
		
		Usuario usuario = client.findByUsername(username);
		
		if(usuario == null) {
			log.error("Error en el login, Usuario no existe: " + usuario);
			throw new UsernameNotFoundException("Error en el login, Usuario no existe: " + usuario);
		}
		
		List<GrantedAuthority> listAuthority = usuario.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getNombre()))
				.peek(authority -> log.info("Role: " + authority.getAuthority()))
				.collect(Collectors.toList());
		
		log.info("Usuario logueado: " + username);
		
		return new User(usuario.getUsername(), usuario.getPassword(), usuario.getEnabled(), true, true, true, listAuthority);
	}

	@Override
	public Usuario findByUsername(String username) {
		return client.findByUsername(username);
	}

}
