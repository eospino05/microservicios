package com.microservicio.commons.entity.model;


import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Personas")
@Data @AllArgsConstructor @NoArgsConstructor
public class Persona implements Serializable{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1328087623282241886L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String identificacion;
    private String nombre;
    private String apellidos;
    private LocalDate fechaNacimiento;
    @Transient
    private Integer puerto; 

    
}
