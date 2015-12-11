package it.polito.applied.asti.clan.pojo;


public class StatusTicket {

	private String idStatus;
	private String code;
	private String description;
	
	public StatusTicket(){
		
	}
	
	public StatusTicket(String idStatus, String code, String description){
		this.idStatus = idStatus;
		this.code = code;
		this.description = description;
	}
	
	public String getIdStatus() {
		return idStatus;
	}
	public void setIdStatus(String idStatus) {
		this.idStatus = idStatus;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
