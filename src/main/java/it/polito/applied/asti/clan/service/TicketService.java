package it.polito.applied.asti.clan.service;

import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.pojo.Read;
import it.polito.applied.asti.clan.pojo.Ticket;
import it.polito.applied.asti.clan.pojo.TicketRequestDTO;

import java.util.List;

public interface TicketService {

	public void operatorGenerateTickets(TicketRequestDTO ticketRequestDTO, String operatorId) throws BadRequestException;
	public List<String> accessiblePlaces(String ticketNumber);
	public List<Ticket> getValidTickets();
	public void savePassingAttempt(Read read);
}
