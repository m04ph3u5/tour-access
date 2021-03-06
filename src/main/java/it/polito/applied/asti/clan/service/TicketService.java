package it.polito.applied.asti.clan.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.exception.ServiceUnaivalableException;
import it.polito.applied.asti.clan.pojo.Poi;
import it.polito.applied.asti.clan.pojo.PoiRank;
import it.polito.applied.asti.clan.pojo.Read;
import it.polito.applied.asti.clan.pojo.RegionRank;
import it.polito.applied.asti.clan.pojo.RoleTicket;
import it.polito.applied.asti.clan.pojo.StatisticsInfo;
import it.polito.applied.asti.clan.pojo.StatusTicket;
import it.polito.applied.asti.clan.pojo.Ticket;
import it.polito.applied.asti.clan.pojo.TicketAccessSeries;
import it.polito.applied.asti.clan.pojo.TicketRequestDTO;

public interface TicketService {

	public void operatorGenerateTickets(TicketRequestDTO ticketRequestDTO, String operatorId) throws BadRequestException, ServiceUnaivalableException;
	public List<String> accessiblePlaces(String ticketNumber);
	public List<Ticket> getValidTickets();
	public void savePassingAttempt(Read read);
	public List<Poi> getAllPlaces();
	public List<RoleTicket> getRoles() throws BadRequestException;
	public List<StatusTicket> getStatus();
	public void pingService() throws ServiceUnaivalableException;
	public long getNumberSelledTicket(Date date);
	public long getNumberIngress(Date date);
	public StatisticsInfo getStatisticsInfo(Date start, Date end);
	public Map<Date, TicketAccessSeries> getTicketAccessSeries(Date start, Date end);
	public List<PoiRank> getPoiRank(Date start, Date end);
	public void operatorDeleteTicket(String id) throws BadRequestException, ServiceUnaivalableException;
	public List<Ticket> getAllTickets();
	public List<RegionRank> getRegionRank(Date start, Date end);
	
	/*New version to manage tickets' group*/
	public void savePassingAttemptV2(Read read);

}
