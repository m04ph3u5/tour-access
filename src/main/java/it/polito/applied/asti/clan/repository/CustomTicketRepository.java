package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.Ticket;

import java.util.Date;
import java.util.List;

public interface CustomTicketRepository {
	public boolean isValid(String[] ticketNumbers, Date start);
	public Ticket findByTicketNumberNow(String ticketNumber);
	public List<Ticket> getValidTickets();
	public void setStartDate(String ticketNumber, Date d);

}
