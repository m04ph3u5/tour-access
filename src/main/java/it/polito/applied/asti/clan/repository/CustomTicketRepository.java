package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.Ticket;
import it.polito.applied.asti.clan.pojo.TotAggregate;

import java.util.Date;
import java.util.List;

public interface CustomTicketRepository {
	public boolean isValid(String[] ticketNumbers, Date start);
	public Ticket findByTicketNumberNow(String ticketNumber);
	public List<Ticket> getValidTickets();
	public void passingAccepted(String ticketNumber, Date d);
	public void removeLastTickets(List<Ticket> tickets);
	public void toReleased(List<Ticket> tickets);
	
	public long totalTickets(Date start, Date end);
	public long countTicketFromDate(Date date);
	List<TotAggregate> getTicketGrouped(Date start, Date end);
}
