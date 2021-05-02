package com.microservicio.oauth.security;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import com.microservicio.commons.entity.model.Usuario;
import com.microservicio.oauth.services.IUsuarioService;

@Component
public class InfoAddToken implements TokenEnhancer{

	@Autowired
	private IUsuarioService usuarioService;
	
	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		Map<String, Object> mapa = new HashMap<String, Object>();
		
		Usuario user = usuarioService.findByUsername(authentication.getName());
		
		mapa.put("nombre", user.getNombre());
		mapa.put("apellido", user.getApellido());
		mapa.put("correo", user.getEmail());
		
		((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(mapa);
		
		return accessToken;
	}

}
