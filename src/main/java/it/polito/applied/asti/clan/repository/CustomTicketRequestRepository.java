package it.polito.applied.asti.clan.repository;

import java.util.Date;

import it.polito.applied.asti.clan.pojo.StatisticsInfo;
import it.polito.applied.asti.clan.pojo.TicketRequest;

public interface CustomTicketRequestRepository {
	
	public long totalGroups(Date start, Date end);
	public long totalSingleTickets(Date start, Date end);
	public long totalSingleManTickets(Date start, Date end);
	public long totalSingleWomanTickets(Date start, Date end);
	public long totalGroupWithChildrenTickets(Date start, Date end);
	public long totalGroupWithOldManTickets(Date start, Date end);
	public long totalSingleYoung(Date start, Date end);
	public long totalSingleMiddleAge(Date start, Date end);
	public long totalSingleElderly(Date start, Date end);
	public void toReleased(TicketRequest ticketRequest);
	public StatisticsInfo getStatisticsInfoGroup(Date start, Date end);
	public StatisticsInfo getStatisticsInfoSingle(Date start, Date end);
}
