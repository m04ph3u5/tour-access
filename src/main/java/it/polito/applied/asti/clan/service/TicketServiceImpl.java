package it.polito.applied.asti.clan.service;

import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.pojo.Place;
import it.polito.applied.asti.clan.pojo.TicketDTO;
import it.polito.applied.asti.clan.repository.PlaceRepository;
import it.polito.applied.asti.clan.repository.TicketRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class TicketServiceImpl implements TicketService{

	@Autowired
	private PlaceRepository placeRepo;
	
	@Autowired
	private TicketRepository ticketRepo;
	
	@Override
	public void generateTickets(TicketDTO ticketDTO) throws BadRequestException {
		if(!placeRepo.isValid(ticketDTO.getPlacesId()))
			throw new BadRequestException("Place id non validi");
	}

	@Override
	public List<Place> validPlaceTicket(String ticketNumber) {
		// TODO Auto-generated method stub
		return null;
	}

}
