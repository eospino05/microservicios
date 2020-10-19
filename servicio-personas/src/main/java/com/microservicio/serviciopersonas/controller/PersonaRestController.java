package com.microservicio.serviciopersonas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import com.microservicio.serviciopersonas.dao.IPersonaRepository;
import com.microservicio.serviciopersonas.exception.NotFoundException;
import com.microservicio.serviciopersonas.exception.ServicesException;
import com.microservicio.serviciopersonas.model.Persona;
import com.microservicio.serviciopersonas.services.IPersosnaServices;
import com.microservicio.serviciopersonas.utils.Constans;


@RestController
@RequestMapping(Constans.URL_BASE_PERSONAS)
public class PersonaRestController{

    @Autowired
    IPersosnaServices persosnaServices;

    @GetMapping
    public ResponseEntity<List<Persona>> list(){

         try {
             return new ResponseEntity(persosnaServices.list(), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity load(@PathVariable("id") Long id){
        Optional<Persona> op;

         try {

            op = persosnaServices.load(id);

            if(op.isPresent()){
                return new ResponseEntity(op.get(), HttpStatus.OK);
            }else{
                return new ResponseEntity(HttpStatus.NOT_FOUND);
                //ResponseEntity(Persona(), HttpStatus.OK)
            }


            //}catch (e:ServicesException){
            //    ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }catch (Exception n){
             return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PostMapping
    public ResponseEntity  insert(@RequestBody Persona persona){

         try {
            persosnaServices.save(persona);

             HttpHeaders responseHeader = new HttpHeaders();
             responseHeader.set("idNuevaPersona", Constans.URL_BASE_PERSONAS + "/" + persona.getId());
             return new ResponseEntity(responseHeader, HttpStatus.CREATED);

        }catch (Exception e){
             return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity  update(@RequestBody Persona persona){

         try {
             return new ResponseEntity( persosnaServices.save(persona), HttpStatus.OK);

        }catch (Exception e){
             return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity  delete(@PathVariable("id") Long id){

         try {
             persosnaServices.remove(id);
             return new ResponseEntity(HttpStatus.OK);

        }catch (NotFoundException n){
            return new ResponseEntity(HttpStatus.NOT_FOUND);
         }catch (Exception e){
             return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
         }
    }

}
