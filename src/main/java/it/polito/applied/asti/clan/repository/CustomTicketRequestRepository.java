package it.polito.applied.asti.clan.repository;

import java.util.Date;
import java.util.List;

import it.polito.applied.asti.clan.pojo.RegionRank;
import it.polito.applied.asti.clan.pojo.TicketRequest;

public interface CustomTicketRequestRepository {
	
	public long totalGroups(Date start, Date end);
	public long totalSingleTickets(Date start, Date end);
	public long totalSingleManTickets(Date start, Date end);
	public long totalSingleWomanTickets(Date start, Date end);
	public long totalSingleYoung(Date start, Date end);
	public long totalSingleMiddleAge(Date start, Date end);
	public long totalSingleElderly(Date start, Date end);
	public void toReleased(TicketRequest ticketRequest);
	public void removeTicketInTicketRequest(String ticketRequestId, String ticketId);
	public long totalCouple(Date start, Date end);
	public long totalFamily(Date start, Date end);
	public long totalSchoolGroup(Date start, Date end);
	public List<RegionRank> getRegionRank(Date start, Date end);
	List<TicketRequest> find(Date start, Date end);
	
	/*New version after group tickets*/
	public List<RegionRank> getRegionRankV2(Date start, Date end);


	/*Method for migration data only. TODO remove it after migration*/
	public List<TicketRequest> getAllAcceptedRequest();
}
