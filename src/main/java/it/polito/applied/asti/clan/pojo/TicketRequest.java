package it.polito.applied.asti.clan.pojo;

import java.util.Date;
import java.util.List;

public class TicketRequest {

	private List<String> ticketNumbers;
	private List<String> placesId;
	private Date startDate;
	private InfoTicketRequest info;
	
	public List<String> getTicketNumbers() {
		return ticketNumbers;
	}
	public void setTicketNumbers(List<String> ticketNumbers) {
		this.ticketNumbers = ticketNumbers;
	}
	public List<String> getPlacesId() {
		return placesId;
	}
	public void setPlacesId(List<String> placesId) {
		this.placesId = placesId;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public InfoTicketRequest getInfo() {
		return info;
	}
	public void setInfo(InfoTicketRequest info) {
		this.info = info;
	}
	
	
	
}
