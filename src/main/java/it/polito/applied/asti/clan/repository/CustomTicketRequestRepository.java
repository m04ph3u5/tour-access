package it.polito.applied.asti.clan.repository;

import java.util.Date;
import java.util.List;

import it.polito.applied.asti.clan.pojo.RegionRank;
import it.polito.applied.asti.clan.pojo.StatisticsInfo;
import it.polito.applied.asti.clan.pojo.TicketRequest;

public interface CustomTicketRequestRepository {
	
	public long totalGroups(Date start, Date end);
	public long totalSingleTickets(Date start, Date end);
	public long totalSingleManTickets(Date start, Date end);
	public long totalSingleWomanTickets(Date start, Date end);
	public long totalGroupWithChildrenTickets(Date start, Date end);
	public long totalGroupWithOldManTickets(Date start, Date end);
	public long totalGroupWithChildrenAndOldManTickets(Date start, Date end);
	public long totalSingleYoung(Date start, Date end);
	public long totalSingleMiddleAge(Date start, Date end);
	public long totalSingleElderly(Date start, Date end);
	public void toReleased(TicketRequest ticketRequest);
//	public StatisticsInfo getStatisticsInfoGroup(Date start, Date end);
	public StatisticsInfo getStatisticsInfoSingle(Date start, Date end);
	public void removeTicketInTicketRequest(String ticketRequestId, String ticketId);
	public long totalCouple(Date start, Date end);
	public long totalFamily(Date start, Date end);
	public long totalSchoolGroup(Date start, Date end);
	public List<RegionRank> getRegionRank(Date start, Date end);
	List<TicketRequest> find(Date start, Date end);

}
