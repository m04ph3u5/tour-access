package it.polito.applied.asti.clan.pojo;

import java.util.Date;
import java.util.List;

public class TicketDTO {

	private List<String> ticketNumbers;
	private List<String> placesId;
	private Date startDate;
	
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
	
	
}
