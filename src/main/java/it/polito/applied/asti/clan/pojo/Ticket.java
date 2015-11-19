package it.polito.applied.asti.clan.pojo;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Ticket {

	@Id
	private String id;
	@NotNull
	@Digits(fraction = 0, integer = 10)
	private String ticketNumber;
	
	private String operatorId;
	private Date validityStartDate;
	private Date validityEndDate;
	
	@NotNull
	private List<String> places;
	
	
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public Date getValidityStartDate() {
		return validityStartDate;
	}
	public void setValidityStartDate(Date validityStartDate) {
		this.validityStartDate = validityStartDate;
	}
	public Date getValidityEndDate() {
		return validityEndDate;
	}
	public void setValidityEndDate(Date validityEndDate) {
		this.validityEndDate = validityEndDate;
	}
	public List<String> getPlaces() {
		return places;
	}
	public void setPlaces(List<String> places) {
		this.places = places;
	}
	public String getId() {
		return id;
	}
	
	
}
