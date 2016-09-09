package it.polito.applied.asti.clan.pojo;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
	private Date dtaExpire;
	private Date dateOnServer;
	
	/*New field inserted to manage tickets' group*/
	private int numPeople;
	
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

	public Date getDtaExpire() {
		return dtaExpire;
	}

	public void setDtaExpire(Date dtaExpire) {
		this.dtaExpire = dtaExpire;
	}

	public int getNumPeople() {
		return numPeople;
	}

	public void setNumPeople(int numPeople) {
		this.numPeople = numPeople;
	}


	
	
	
}
