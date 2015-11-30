package it.polito.applied.asti.clan.service;

import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.pojo.Read;
import it.polito.applied.asti.clan.pojo.Ticket;
import it.polito.applied.asti.clan.pojo.TicketRequest;
import it.polito.applied.asti.clan.pojo.TicketRequestDTO;
import it.polito.applied.asti.clan.repository.PoiRepository;
import it.polito.applied.asti.clan.repository.ReadRepository;
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
	private ReadRepository readRepo;
	
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
		
		
		if(!ticketRepo.isValid(ticketRequestDTO.getTicketsNumber().toArray(new String [0]),startForValidation)){
			if(ticketRequestDTO.getTicketsNumber().size()==1)
				throw new BadRequestException("Ticket gia' prenotato per la data selezionata");
			else
				throw new BadRequestException("Ticket gia' prenotati per la data selezionata");
		}
			
		
		TicketRequest ticketRequest = new TicketRequest(ticketRequestDTO, operatorId);
		ticketRequest = ticketRequestRepo.save(ticketRequest);
		
		List<Ticket> tickets = new ArrayList<Ticket>();
		for(String n : ticketRequest.getTicketNumbers()){
			System.out.println("Prova "+n);
			Ticket t = new Ticket();
			t.setTicketRequestId(ticketRequest.getId());
			t.setSites(ticketRequest.getPlacesId());
			t.setIdTicket(n);
			t.setStartDate(start);  //TODO decidere se va bene
			t.setEmissionDate(new Date());
			t.setEndDate(end);
			
			//TODO da decidere come e quando settare la duration
			
			//TODO controllare il tipo di ruolo, per semplificare ipotizzo che DAILY_VISITOR valga 1 e setto a prescindere questo valore come role.
			//Dovrei in realtà capire dal TicketRequestDTO che tipo di ruolo deve assumere il biglietto, interrogare il db per controllare che esista
			//e prendere il codice giusto.
			t.setRole(1);
			t.setStatus("RELEASED");
			
			tickets.add(t);
			
		}
		
		ticketRepo.save(tickets);
			
	}

	@Override
	public List<String> accessiblePlaces(String idTicket) {
		List<Ticket> list = ticketRepo.findByIdTicket(idTicket);
		
		Ticket t = list.get(0);
		if(t!=null)
			return t.getSites();
		else
			return new ArrayList<String>();
	}

	@Override
	public List<Ticket> getValidTickets() {
		List<Ticket> tickets = new ArrayList<Ticket>();
		tickets = ticketRepo.getValidTickets();
		return tickets;
	}

	@Override
	public void savePassingAttempt(Read read) {
		//salvo la lettura nel db così come mi arriva + orario sul server
		readRepo.save(read);
		//TODO modifica startDate e endDate del biglietto se il passaggio è stato accettato ed il biglietto era ancora inutilizzato
		if(read.isAccepted()){
			ticketRepo.setStartDate(read.getTicketNumber(), read.getDate());
		}
		
	}

}
