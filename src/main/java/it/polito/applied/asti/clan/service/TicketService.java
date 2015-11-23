package it.polito.applied.asti.clan.service;

import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.pojo.Poi;
import it.polito.applied.asti.clan.pojo.TicketRequest;

import java.util.List;

public interface TicketService {

	public void operatorGenerateTickets(TicketRequest ticketRequest, String operatorId) throws BadRequestException;
	public List<String> accessiblePlaces(String ticketNumber);
}
