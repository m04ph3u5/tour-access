package it.polito.applied.asti.clan.pojo;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes(value = 
{
    @CompoundIndex (name = "TicketNumber_EndDate", def = "{'ticketNumber': 1, 'validityEndDate': -1}", unique = true)
}
)public class Ticket {

	@Id
	private String id;
	@NotNull
	@Digits(fraction = 0, integer = 10)
	@Indexed
	private String ticketNumber;
	
	private String ticketRequestId;
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
	
	public String getTicketRequestId() {
		return ticketRequestId;
	}
	public void setTicketRequestId(String ticketRequestId) {
		this.ticketRequestId = ticketRequestId;
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
