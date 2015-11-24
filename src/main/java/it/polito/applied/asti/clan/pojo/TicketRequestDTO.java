package it.polito.applied.asti.clan.pojo;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

public class TicketRequestDTO {
	
	@NotNull
	private List<String> placesId;
	@NotNull
	private Set<String> ticketsNumber;
	private Date startDate;
	@NotNull
	private InfoTicketRequest info;
	
	public List<String> getPlacesId() {
		return placesId;
	}
	public void setPlacesId(List<String> placesId) {
		this.placesId = placesId;
	}
	public Set<String> getTicketsNumber() {
		return ticketsNumber;
	}
	public void setTicketsNumber(Set<String> ticketsNumber) {
		this.ticketsNumber = ticketsNumber;
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
