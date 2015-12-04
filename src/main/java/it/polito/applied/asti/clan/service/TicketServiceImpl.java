package it.polito.applied.asti.clan.service;

import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.pojo.Poi;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl implements TicketService{

	/*
	status.released.id = s00001
	status.validated.id = s00002
	status.canceled.id = s00003
	 */
	@Value("${status.canceled.id}")
	private String CANCELED;
	@Value("${status.validated.id}")
	private String VALIDATED;
	@Value("${status.released.id}")
	private String RELEASED;
	
	/*
	role.dailyVisitor.id = r00001
	role.weeklyVisitor.id = r00002
	role.dailyVipVisitor.id = r00003
	role.weeklyVipVisitor.id = r00004
	role.service.id = r00005
	role.supervisor.id = r00006
	 */
	@Value("${role.dailyVisitor.id}")
	private String DAILY_VISITOR;
	@Value("${role.weeklyVisitor.id}")
	private String WEEKLY_VISITOR;
	@Value("${role.dailyVipVisitor.id}")
	private String DAILY_VIP_VISITOR;
	@Value("${role.weeklyVipVisitor.id}")
	private String WEEKLY_VIP_VISITOR;
	@Value("${role.service.id}")
	private String SERVICE;
	@Value("${role.supervisor.id}")
	private String SUPERVISOR;
	
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
		c.add(Calendar.YEAR, 1);
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
			
			//setto il ruoloe lo stato del/dei biglietto/i
			
			if(ticketRequest.getTipology().equals("DAILY_VISITOR")){
				t.setRole(DAILY_VISITOR);
				t.setStatus(RELEASED);
				
			}else if(ticketRequest.getTipology().equals("WEEKLY_VISITOR")){
				t.setRole(WEEKLY_VISITOR);
				t.setStatus(RELEASED);
				
			}else if(ticketRequest.getTipology().equals("DAILY_VIP_VISITOR")){
				t.setRole(DAILY_VIP_VISITOR);
				t.setStatus(RELEASED);
				
			}else if(ticketRequest.getTipology().equals("WEEKLY_VIP_VISITOR")){
				t.setRole(WEEKLY_VIP_VISITOR);
				t.setStatus(RELEASED);
				
			}else if(ticketRequest.getTipology().equals("SERVICE")){
				t.setRole(SERVICE);
				t.setStatus(VALIDATED);
				
			}else if(ticketRequest.getTipology().equals("SUPERVISOR")){
				t.setRole(SUPERVISOR);
				t.setStatus(VALIDATED);
				
			}else
				throw new BadRequestException("Tipologia ticket non supportata!");
			
			t.setRole(ticketRequest.getTipology());
			
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
		//salvo la lettura nel db (cos� come mi arriva dal client e con l'aggiunta dell'orario corrente sul server)
		readRepo.save(read);
		//TODO modifica startDate e endDate del biglietto se il passaggio � stato accettato ed il biglietto era ancora inutilizzato
		if(read.isAccepted()){
			ticketRepo.passingAccepted(read.getTicketNumber(), read.getDate());
		}
		
	}

	@Override
	public List<Poi> getAllPlaces() {
		// TODO Auto-generated method stub
		return poiRepo.findAllPlaceCustom();
	}

}
