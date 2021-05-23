package com.microservicio.oauth.security.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.microservicio.commons.entity.model.Usuario;
import com.microservicio.oauth.services.IUsuarioService;

import feign.FeignException;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher{

	private Logger log =  LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		UserDetails user = (UserDetails)authentication.getPrincipal();
		
		if(!authentication.getName().equals("frontrnd")) {
			Usuario usuario = usuarioService.findByUsername(user.getUsername());
			if(usuario.getIntentos() != null && usuario.getIntentos() >= 3) {
				usuario.setIntentos(0);
				usuarioService.update(usuario, usuario.getId());
			}
		}
		
		
		String mensaje = "Usuario logueado: " + user.getUsername();
		
		log.info(mensaje);
		System.out.println(mensaje);
		
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		String mensaje = String.format("Error al loguearse usuario: %s, Error: %s", authentication.getName(), exception.getMessage());
		
		log.error(mensaje);
		System.out.println(mensaje);
		
		try {
			Usuario usuario = usuarioService.findByUsername(authentication.getName());
			usuario.setIntentos(usuario.getIntentos() != null ? usuario.getIntentos() + 1 : 1);
			
			log.error(String.format("Número de intentos: %s", usuario.getIntentos()));
			
			if(usuario.getIntentos() >= 3) {
				usuario.setEnabled(false);
				log.error(String.format("Ususario %s deshabilitado por máximo intentos", authentication.getName()));
			}
				
			
			usuarioService.update(usuario, usuario.getId());
		}catch (FeignException e) {
			log.error(String.format("Ususario %s no existe", authentication.getName()));
		}
		
		
		
	}

}
