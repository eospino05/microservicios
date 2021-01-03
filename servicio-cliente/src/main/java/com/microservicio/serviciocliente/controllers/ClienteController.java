package com.microservicio.serviciocliente.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.serviciocliente.model.Grupo;
import com.microservicio.serviciocliente.model.Persona;
import com.microservicio.serviciocliente.model.services.ClienteServicio;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RefreshScope
@RestController
//@RequestMapping("/api/cliente/personas")

public class ClienteController {

	@Autowired
	//@Qualifier("servicioRestTemplate")
	@Qualifier("servicioFeing")
	ClienteServicio clienteServicio;
	
	@Autowired
	private Environment env;
	
	@Value("${configuracion.texto}")
	private String texto;
	
	 @GetMapping
	    public ResponseEntity<List<Grupo>> list(){

	         try {
	             return new ResponseEntity(clienteServicio.findAll(), HttpStatus.OK);
	        }catch (Exception e){
	            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	 	//@HystrixCommand(fallbackMethod = "buscarAlterno")
	    @GetMapping("/{id}/grupo/{grupo}")
	    public ResponseEntity load(@PathVariable("id") Long id, @PathVariable("grupo") String grupo){

	         try {
	        	 return new ResponseEntity(clienteServicio.findById(id, grupo), HttpStatus.OK);
	          
	        }catch (Exception n){
	             return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

	        }
	    }
	    
	    public ResponseEntity buscarAlterno(Long id, String grupo) {
	    	Persona p = new Persona();
	    	p.setId(id);
	    	p.setNombre("Pepito");
	    	p.setIdentificacion("123322");
	    	p.setApellidos("Perez Prieto");
	    	
	    	Grupo g = new Grupo(p, grupo);
	    	
	    	return new ResponseEntity(g, HttpStatus.OK);
	    }
	    
	    @GetMapping("/obtener-config")
	    public ResponseEntity<?> load(@Value("${server.port}") String puerto){
	    	
	    	Map<String, String> json = new HashMap<String, String>();
	    	json.put("texto", texto);
	    	json.put("puerto", puerto);
	    	
	    	if(env.getActiveProfiles().length > 0 && env.getActiveProfiles()[0].equals("dev")) {
	    		json.put("autor.nombre", env.getProperty("configuracion.autor.nombre"));
	    		json.put("autor.email", env.getProperty("configuracion.autor.email"));
	    	}
	    	
	    	return new ResponseEntity<Map<String, String>>(json, HttpStatus.OK);
	          
	       
	    }
	    
	    
}
