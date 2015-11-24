package it.polito.applied.asti.clan.service;

import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.pojo.Ticket;
import it.polito.applied.asti.clan.pojo.TicketRequest;
import it.polito.applied.asti.clan.pojo.TicketRequestDTO;
import it.polito.applied.asti.clan.repository.PoiRepository;
import it.polito.applied.asti.clan.repository.TicketRepository;
import it.polito.applied.asti.clan.repository.TicketRequestRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService{

	@Autowired
	private PoiRepository poiRepo;
	
	@Autowired
	private TicketRepository ticketRepo;
	
	@Autowired
	private TicketRequestRepository ticketRequestRepo;
	
	@Override
	public void operatorGenerateTickets(TicketRequestDTO ticketRequestDTO, String operatorId) throws BadRequestException {
		if(!poiRepo.isValid(ticketRequestDTO.getPlacesId()))
			throw new BadRequestException("Place id non validi");
		
		Date start = ticketRequestDTO.getStartDate();
		Date end;
		Calendar c = Calendar.getInstance();
		
		if(start==null)
			start = new Date();
		else{
			Date today = new Date();
			c.setTime(today);
			c.set(Calendar.HOUR_OF_DAY,0);
			c.set(Calendar.MINUTE,0);
			c.set(Calendar.SECOND, 0);
			today = c.getTime();
			
			if(start.before(today))
				throw new BadRequestException();
		}
			
		Date startForValidation;
		c.setTime(start);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND, 0);
		startForValidation = c.getTime();
		c.setTime(start);
		c.set(Calendar.HOUR_OF_DAY,23);
		c.set(Calendar.MINUTE,59);
		c.set(Calendar.SECOND, 59);
		end = c.getTime();
		
		
		if(!ticketRepo.isValid(ticketRequestDTO.getTicketsNumber(),startForValidation, end))
			throw new BadRequestException("Ticket già prenotati per le date selezionate");
		
		TicketRequest ticketRequest = new TicketRequest(ticketRequestDTO, operatorId);
		ticketRequest = ticketRequestRepo.save(ticketRequest);
		
		List<Ticket> tickets = new ArrayList<Ticket>();
		for(String n : ticketRequest.getTicketNumbers()){
			Ticket t = new Ticket();
			t.setTicketRequestId(ticketRequest.getId());
			t.setPlaces(ticketRequest.getPlacesId());
			t.setTicketNumber(n);
			t.setValidityStartDate(start);
			t.setValidityEndDate(end);
			tickets.add(t);
		}
		
		ticketRepo.save(tickets);
			
	}

	@Override
	public List<String> accessiblePlaces(String ticketNumber) {
		List<Ticket> list = ticketRepo.findByTicketNumber(ticketNumber);
		
		Ticket t = list.get(0);
		if(t!=null)
			return t.getPlaces();
		else
			return new ArrayList<String>();
	}

}
