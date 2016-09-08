package it.polito.applied.asti.clan.pojo;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes(value = 
{
    @CompoundIndex (name = "idTicket_EndDate", def = "{'idTicket': 1, 'endDate': -1}", unique = true)
}
)public class Ticket {

	@Id
	private String id;
	@NotNull
	@Digits(fraction = 0, integer = 10)
	@Indexed
	private String idTicket; //(numero su 10 cifre): identificativo univoco numerico integrato nel biglietto stesso;
	private int role; //identificativo del ruolo associato al biglietto;
	private String ticketRequestId;
	
	private Date emissionDate; //definisce data e ora di emissione del biglietto;
	private Date startDate; //definisce data e ora del primo accesso (all'emissione del biglietto potrebbe corrispondere a emissionDate oppure essere messo a null);
	private Date endDate; //definisce data e ora della scadenza del biglietto (biglietto non pi� valido per l'accesso). Inizialmente contiene la scadenza del biglietto emesso (da definire);
	
	private String status; //(6 caratteri alfanumerici): identificativo dello stato associato al biglietto;
	private int duration; //potremmo recuperarla dal ruolo (da decidere se poter customizzare la durata dei vari biglietti in fase di acquisto,scorrelandola dal ruolo).
	@NotNull
	private List<String> sites; //lista degli identificativi dei siti a cui � consentito l'accesso nell'intervallo di tempo specificato;
	
	@Min(1)
	@Max(50)
	private int numPeople;
	
	
	public Date getEmissionDate() {
		return emissionDate;
	}
	public void setEmissionDate(Date emissionDate) {
		this.emissionDate = emissionDate;
	}
	public int getRole() {
		return role;
	}
	public void setRole(int role) {
		this.role = role;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	
	public String getIdTicket() {
		return idTicket;
	}
	public void setIdTicket(String idTicket) {
		this.idTicket = idTicket;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getTicketRequestId() {
		return ticketRequestId;
	}
	public void setTicketRequestId(String ticketRequestId) {
		this.ticketRequestId = ticketRequestId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public List<String> getSites() {
		return sites;
	}
	public void setSites(List<String> sites) {
		this.sites = sites;
	}
	public String getId() {
		return id;
	}
	public int getNumPeople() {
		return numPeople;
	}
	public void setNumPeople(int numPeople) {
		this.numPeople = numPeople;
	}	
	
}
