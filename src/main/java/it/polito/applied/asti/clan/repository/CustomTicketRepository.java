package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.Ticket;

import java.util.Date;
import java.util.List;

public interface CustomTicketRepository {
	public boolean isValid(List<String> ticketNumbers, Date start, Date end);
	public Ticket findByTicketNumberNow(String ticketNumber);

}
