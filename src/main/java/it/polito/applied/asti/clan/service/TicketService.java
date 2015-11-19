package it.polito.applied.asti.clan.service;

import it.polito.applied.asti.clan.pojo.Place;
import it.polito.applied.asti.clan.pojo.TicketDTO;

import java.util.List;

public interface TicketService {

	public void generateTickets(TicketDTO ticketDTO);
	public List<Place> validPlaceTicket(String ticketNumber);
}
