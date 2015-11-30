package it.polito.applied.asti.clan.pojo;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author andrea
 *
 */
@Document
public class Read {

	private String id;
	private String idSite;
	private String ticketNumber;
	private boolean accepted;
	private Date date;
	private String reason; //null if accepted=true
	
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
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public boolean isAccepted() {
		return accepted;
	}
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	
}
