package it.polito.applied.asti.clan.service;

import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.exception.ServiceUnaivalableException;
import it.polito.applied.asti.clan.pojo.Poi;
import it.polito.applied.asti.clan.pojo.Read;
import it.polito.applied.asti.clan.pojo.RoleTicket;
import it.polito.applied.asti.clan.pojo.StatusTicket;
import it.polito.applied.asti.clan.pojo.Ticket;
import it.polito.applied.asti.clan.pojo.TicketRequestDTO;

import java.util.List;

public interface TicketService {

	public void operatorGenerateTickets(TicketRequestDTO ticketRequestDTO, String operatorId) throws BadRequestException, ServiceUnaivalableException;
	public List<String> accessiblePlaces(String ticketNumber);
	public List<Ticket> getValidTickets();
	public void savePassingAttempt(Read read);
	public List<Poi> getAllPlaces();
	public List<RoleTicket> getRoles() throws BadRequestException;
	public List<StatusTicket> getStatus();
	public void pingService() throws ServiceUnaivalableException;
}
