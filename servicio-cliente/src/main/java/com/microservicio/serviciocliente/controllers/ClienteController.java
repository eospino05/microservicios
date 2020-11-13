package com.microservicio.serviciocliente.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservicio.serviciocliente.model.Grupo;
import com.microservicio.serviciocliente.model.services.ClienteServicio;

@RestController
@RequestMapping("/api/cliente/personas")
public class ClienteController {

	@Autowired
	ClienteServicio clienteServicio;
	
	 @GetMapping
	    public ResponseEntity<List<Grupo>> list(){

	         try {
	             return new ResponseEntity(clienteServicio.findAll(), HttpStatus.OK);
	        }catch (Exception e){
	            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }

	    @GetMapping("/{id}/grupo/{grupo}")
	    public ResponseEntity load(@PathVariable("id") Long id, @PathVariable("grupo") String grupo){

	         try {
	        	 return new ResponseEntity(clienteServicio.findById(id, grupo), HttpStatus.OK);
	          
	        }catch (Exception n){
	             return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

	        }
	    }
}
