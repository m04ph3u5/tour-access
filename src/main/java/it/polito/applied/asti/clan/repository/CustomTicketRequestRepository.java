package it.polito.applied.asti.clan.repository;

import java.util.Date;

public interface CustomTicketRequestRepository {
	
	public long totalGroupTickets(Date start, Date end);
	public long totalSingleTickets(Date start, Date end);
	public long totalSingleManTickets(Date start, Date end);
	public long totalSingleWomanTickets(Date start, Date end);
	public long totalGroupWithChildrenTickets(Date start, Date end);
	public long totalGroupWithOldManTickets(Date start, Date end);
	long totalSingleYoung(Date start, Date end);
	long totalSingleMiddleAge(Date start, Date end);
	long totalSingleElderly(Date start, Date end);
}
