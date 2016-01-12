package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.TicketAggregate;

import java.util.Date;
import java.util.List;

public interface CustomTicketRequestRepository {
	
	public long totalGroupTickets(Date start, Date end);
	public long totalSingleTickets(Date start, Date end);
	public long totalSingleManTickets(Date start, Date end);
	public long totalSingleWomanTickets(Date start, Date end);
	public long totalGroupWithChildrenTickets(Date start, Date end);
	public long totalGroupWithOldManTickets(Date start, Date end);
}
