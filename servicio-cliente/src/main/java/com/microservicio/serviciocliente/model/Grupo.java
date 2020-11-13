package com.microservicio.serviciocliente.model;


public class Grupo {
	
	private Persona persona;
	private String nmGrupo;
	
	public Grupo() {
	}
	
	public Grupo(Persona persona, String nmGrupo) {
		this.persona = persona;
		this.nmGrupo = nmGrupo;
	}
	
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public String getNmGrupo() {
		return nmGrupo;
	}
	public void setNmGrupo(String nmGrupo) {
		this.nmGrupo = nmGrupo;
	}
	
}
