package it.polito.applied.asti.clan.pojo;


public class RoleTicket {

	private int idRole;  //(numero su 1 byte): identificativo univoco del ruolo;
	private String code; //identificativo human readable
	private String description; //descrizione pi� dettagliata del ruolo
	private boolean antiPassBack; //): se true, indica che a questo ruolo si applica la politica antiPassBack (ogni volta che viene rilevato  l�ingresso ad un sito, viene inibito, per il giorno corrente, l�ulteriore accesso al sito).
	
	public RoleTicket(){
		
	}
	
	public RoleTicket(int idRole, String role, String description, boolean antiPassBack){
		this.idRole = idRole;
		this.code = role;
		this.description = description;
		this.antiPassBack = antiPassBack;
	}

	public void setIdRole(int idRole) {
		this.idRole = idRole;
	}


	public int getIdRole() {
		return idRole;
	}
	public void setId(int idRole) {
		this.idRole = idRole;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String role) {
		this.code = role;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isAntiPassBack() {
		return antiPassBack;
	}
	public void setAntiPassBack(boolean antiPassBack) {
		this.antiPassBack = antiPassBack;
	}
	
	
}
