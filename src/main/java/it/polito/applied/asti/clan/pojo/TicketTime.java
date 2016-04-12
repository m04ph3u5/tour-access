package it.polito.applied.asti.clan.pojo;

import java.util.Date;

public class TicketTime {
	
	private String ticketNumber;
	private Date date;
	
	public TicketTime(){
		
	}
	
	public TicketTime(String ticketNumber, Date date){
		this.ticketNumber = ticketNumber;
		this.date = date;
	}
	
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
