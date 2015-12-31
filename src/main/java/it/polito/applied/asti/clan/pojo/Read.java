package it.polito.applied.asti.clan.pojo;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author andrea
 *
 */
@Document
public class Read {

	@Id
	private String id;
	@NotNull
	private String idSite;
	@NotNull
	private String idTicket;
	@NotNull
	private Boolean isAccepted;
	private Date dtaTransit;
	private String desError; //null if accepted=true
	
	private Date dateOnServer;
	
	
	public Read(){
		
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDateOnServer() {
		return dateOnServer;
	}

	public void setDateOnServer(Date dateOnServer) {
		this.dateOnServer = dateOnServer;
	}

	public String getIdSite() {
		return idSite;
	}
	public void setIdSite(String idSite) {
		this.idSite = idSite;
	}

	public String getIdTicket() {
		return idTicket;
	}

	public void setIdTicket(String idTicket) {
		this.idTicket = idTicket;
	}

	public Boolean getIsAccepted() {
		return isAccepted;
	}

	public void setIsAccepted(Boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	public Date getDtaTransit() {
		return dtaTransit;
	}

	public void setDtaTransit(Date dtaTransit) {
		this.dtaTransit = dtaTransit;
	}

	public String getDesError() {
		return desError;
	}

	public void setDesError(String desError) {
		this.desError = desError;
	}

	
	
	
}
