package it.polito.applied.asti.clan.pojo;

import java.util.List;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TicketRequestDTO {
	
	@NotNull
	private List<String> placesId;
	@NotNull
	private String ticketNumber;
	@NotNull
	private InfoTicketRequest info;
	private String tipology;
	
	@Min(1)
	@Max(50)
	private int numPeople;
	
	public String getTipology() {
		return tipology;
	}
	public void setTipology(String tipology) {
		this.tipology = tipology;
	}
	public List<String> getPlacesId() {
		return placesId;
	}
	public void setPlacesId(List<String> placesId) {
		this.placesId = placesId;
	}
	
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public InfoTicketRequest getInfo() {
		return info;
	}
	public void setInfo(InfoTicketRequest info) {
		this.info = info;
	}
	public int getNumPeople() {
		return numPeople;
	}
	public void setNumPeople(int numPeople) {
		this.numPeople = numPeople;
	}
	
	

}
