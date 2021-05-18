package com.microservicio.oauth.security.event;

import org.apache.logging.log4j.simple.SimpleLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessErrorHandler implements AuthenticationEventPublisher{

	private Logger log =  LoggerFactory.getLogger(AuthenticationSuccessErrorHandler.class);
	@Override
	public void publishAuthenticationSuccess(Authentication authentication) {
		UserDetails user = (UserDetails)authentication.getPrincipal();
		
		String mensaje = "Usuario logueado: " + user.getUsername();
		
		log.info(mensaje);
		System.out.println(mensaje);
		
	}

	@Override
	public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
		UserDetails user = (UserDetails)authentication.getPrincipal();
		
		String mensaje = String.format("Error al loguearse usuario: %s, Error: %s", user.getUsername(), exception.getMessage());
		
		log.error(mensaje);
		System.out.println(mensaje);
		
	}

}
