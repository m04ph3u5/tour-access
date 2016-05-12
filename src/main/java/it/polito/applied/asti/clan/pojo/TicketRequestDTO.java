package it.polito.applied.asti.clan.pojo;

import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

public class TicketRequestDTO {
	
	@NotNull
	private List<String> placesId;
	@NotNull
	private Set<String> ticketsNumber;
	@NotNull
	private InfoTicketRequest info;
	private String tipology;
	
	
	
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
	public Set<String> getTicketsNumber() {
		return ticketsNumber;
	}
	public void setTicketsNumber(Set<String> ticketsNumber) {
		this.ticketsNumber = ticketsNumber;
	}
	
	public InfoTicketRequest getInfo() {
		return info;
	}
	public void setInfo(InfoTicketRequest info) {
		this.info = info;
	}
	
	

}
