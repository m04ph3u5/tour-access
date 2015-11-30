package it.polito.applied.asti.clan.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class StatusTicket {

	@Id
	private String idStatus;
	private String code;
	private String description;
	
	
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
