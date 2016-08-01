package it.polito.applied.asti.clan.repository;

import java.util.Date;
import java.util.List;
import java.util.Set;

import it.polito.applied.asti.clan.pojo.Ticket;
import it.polito.applied.asti.clan.pojo.TotAggregate;

public interface CustomTicketRepository {
	public boolean isValid(String[] ticketNumbers, Date start);
	public Ticket findByTicketNumberNow(String ticketNumber);
	public List<Ticket> getValidTickets();
	public List<Ticket> getAllTickets();
	public void passingAccepted(String ticketNumber, Date passed, Date expiration);
	public void removeLastTickets(List<Ticket> tickets);
	public void toReleased(List<Ticket> tickets);
	
	public long totalTickets(Date start, Date end);
	public long countTicketFromDate(Date date);
	public List<TotAggregate> getTicketGrouped(Date start, Date end);
	public Ticket findLastTicket(String id);
	public void removeById(String id);
	public void moveToDeleted(String id);
	public List<Ticket> findInList(Set<String> ticketNumbers, Date start, Date end);
	public List<Ticket> findReleasedNotService();
}
