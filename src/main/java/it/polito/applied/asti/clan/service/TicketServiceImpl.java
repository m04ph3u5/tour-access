package it.polito.applied.asti.clan.service;

import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.exception.ServiceUnaivalableException;
import it.polito.applied.asti.clan.pojo.Poi;
import it.polito.applied.asti.clan.pojo.PoiRank;
import it.polito.applied.asti.clan.pojo.Read;
import it.polito.applied.asti.clan.pojo.RoleTicket;
import it.polito.applied.asti.clan.pojo.StatisticsInfo;
import it.polito.applied.asti.clan.pojo.StatusTicket;
import it.polito.applied.asti.clan.pojo.Ticket;
import it.polito.applied.asti.clan.pojo.TicketAccessSeries;
import it.polito.applied.asti.clan.pojo.TicketRequest;
import it.polito.applied.asti.clan.pojo.TicketRequestDTO;
import it.polito.applied.asti.clan.pojo.TotAggregate;
import it.polito.applied.asti.clan.repository.PoiRepository;
import it.polito.applied.asti.clan.repository.ReadRepository;
import it.polito.applied.asti.clan.repository.TicketRepository;
import it.polito.applied.asti.clan.repository.TicketRequestRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.JSONException;
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
	@Value("${status.pending.id}")
	private String PENDING;
	/*
	role.dailyVisitor.id = 1
	role.weeklyVisitor.id = 2
	role.dailyVipVisitor.id = 3
	role.weeklyVipVisitor.id = 4
	role.service.id = 5
	role.supervisor.id = 6
	 */
	@Value("${role.dailyVisitor.id}")
	private int DAILY_VISITOR;
	@Value("${role.weeklyVisitor.id}")
	private int WEEKLY_VISITOR;
	@Value("${role.dailyVipVisitor.id}")
	private int DAILY_VIP_VISITOR;
	@Value("${role.weeklyVipVisitor.id}")
	private int WEEKLY_VIP_VISITOR;
	@Value("${role.service.id}")
	private int SERVICE;
	@Value("${role.supervisor.id}")
	private int SUPERVISOR;
	
	@Autowired
	private PoiRepository poiRepo;
	
	@Autowired
	private TicketRepository ticketRepo;
	
	@Autowired
	private ReadRepository readRepo;
	
	@Autowired
	private TicketRequestRepository ticketRequestRepo;
	
	@Autowired
	private UtilPostToAclTask postToAcl;
	
	@Override
	public void operatorGenerateTickets(TicketRequestDTO ticketRequestDTO, String operatorId) throws BadRequestException, ServiceUnaivalableException {
//		if(!poiRepo.isValid(ticketRequestDTO.getPlacesId()))
//			throw new BadRequestException("Place id non validi");
		
		Date end;
		Calendar c = Calendar.getInstance();
		
		Date start = new Date();
		
			
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
			t.setEmissionDate(start);
			t.setEndDate(end);
			
			//TODO da decidere come e quando settare la duration
			
			//setto il ruoloe lo stato del/dei biglietto/i
			
			if(ticketRequest.getTipology().equals("DAILY_VISITOR")){
				t.setRole(DAILY_VISITOR);
				t.setStatus(PENDING);
				
			}else if(ticketRequest.getTipology().equals("WEEKLY_VISITOR")){
				t.setRole(WEEKLY_VISITOR);
				t.setStatus(PENDING);
				
			}else if(ticketRequest.getTipology().equals("DAILY_VIP_VISITOR")){
				t.setRole(DAILY_VIP_VISITOR);
				t.setStatus(PENDING);
				
			}else if(ticketRequest.getTipology().equals("WEEKLY_VIP_VISITOR")){
				t.setRole(WEEKLY_VIP_VISITOR);
				t.setStatus(PENDING);
				
//			}else if(ticketRequest.getTipology().equals("SERVICE")){
//				t.setRole(SERVICE);
//				t.setStatus(VALIDATED);
				
//			}else if(ticketRequest.getTipology().equals("SUPERVISOR")){
//				t.setRole(SUPERVISOR);
//				t.setStatus(VALIDATED);
//				
//			}
			}else
				throw new BadRequestException("Tipologia ticket non supportata!");
						
			tickets.add(t);
			
		}
		
		ticketRepo.save(tickets);
		try {
			postToAcl.sendTicketsToAcl(tickets);
		} catch (JSONException | IOException e) {
//			ticketRepo.removeLastTickets(tickets);
			throw new ServiceUnaivalableException("Connessione col server non disponibile. Riprovare più tardi.");
		}
		ticketRepo.toReleased(tickets);	
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
		if(read.getIsAccepted()){
			ticketRepo.passingAccepted(read.getIdTicket(), read.getDtaTransit());
		}
		
	}

	@Override
	public List<Poi> getAllPlaces() {
		// TODO Auto-generated method stub
		return poiRepo.findAllPlaceCustom();
	}

	@Override
	public List<RoleTicket> getRoles() throws BadRequestException {
		List<RoleTicket> roles = new ArrayList<RoleTicket>();
		
		RoleTicket r1 = new RoleTicket(DAILY_VISITOR, "DAILY_VISITOR","" ,true);
		roles.add(r1);
		
		RoleTicket r2 = new RoleTicket(WEEKLY_VISITOR, "WEEKLY_VISITOR","" ,true);
		roles.add(r2);
		
		RoleTicket r3 = new RoleTicket(DAILY_VIP_VISITOR, "DAILY_VIP_VISITOR","" ,false);
		roles.add(r3);
		
		RoleTicket r4 = new RoleTicket(WEEKLY_VIP_VISITOR, "WEEKLY_VIP_VISITOR","" ,false);
		roles.add(r4);
		
		RoleTicket r5 = new RoleTicket(SERVICE, "SERVICE","" ,false);
		roles.add(r5);
		
		RoleTicket r6 = new RoleTicket(SUPERVISOR, "SUPERVISOR","" ,false);
		roles.add(r6);
		
		return roles;
	}

	@Override
	public List<StatusTicket> getStatus() {
		List<StatusTicket> status = new ArrayList<StatusTicket>();
		
		StatusTicket s1 = new StatusTicket(RELEASED,"RELEASED","");
		status.add(s1);
		
		StatusTicket s2 = new StatusTicket(VALIDATED,"VALIDATED","");
		status.add(s2);
		
		StatusTicket s3 = new StatusTicket(CANCELED,"CANCELED","");
		status.add(s3);
		
		return status;
	}

	@Override
	public void pingService() throws ServiceUnaivalableException {
		try {
			postToAcl.ping();
		} catch (BadRequestException | IOException e) {
			throw new ServiceUnaivalableException("IO|BAD: "+e.getMessage());
		}
	}

	@Override
	public long getNumberSelledTicket(Date date) {
		return ticketRepo.countTicketFromDate(date);
	}

	@Override
	public long getNumberIngress(Date date) {
		return readRepo.countIngressFromDate(date);
	}

	@Override
	public StatisticsInfo getStatisticsInfo(Date start, Date end) {
		StatisticsInfo s = new StatisticsInfo();
		s.setTotSingleTickets(ticketRequestRepo.totalSingleTickets(start, end));
		s.setTotGroupTickets(ticketRequestRepo.totalGroupTickets(start, end));
		s.setTotMale(ticketRequestRepo.totalSingleManTickets(start, end));
		s.setTotFemale(ticketRequestRepo.totalSingleWomanTickets(start, end));
		s.setTotChildren(ticketRequestRepo.totalGroupWithChildrenTickets(start, end));
		s.setTotElderly(ticketRequestRepo.totalGroupWithOldManTickets(start, end));
		s.setYoung(ticketRequestRepo.totalSingleYoung(start, end));
		s.setMiddleAge(ticketRequestRepo.totalSingleMiddleAge(start, end));
		s.setElderly(ticketRequestRepo.totalSingleElderly(start, end));
		s.setTotAccess(readRepo.countIngress(start, end));
		s.setTotTickets(ticketRepo.totalTickets(start, end));
		return s;
	}

	@Override
	public Map<Date, TicketAccessSeries> getTicketAccessSeries(Date start, Date end) {
		TreeMap<Date, TicketAccessSeries> map = new TreeMap<Date, TicketAccessSeries>();
		List<TotAggregate> access = readRepo.getAccessGrouped(start, end);
		List<TotAggregate> ticket = ticketRepo.getTicketGrouped(start, end);
		Calendar c = Calendar.getInstance();
		c.setTime(end);
		c.set(Calendar.HOUR_OF_DAY,23);
		c.set(Calendar.MINUTE,59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		end = c.getTime();

		c.set(Calendar.YEAR, 2015);
		c.set(Calendar.MONTH, 11);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		Date first = c.getTime();
		
		c.setTime(start);

		if(start.before(first)){
			c.set(Calendar.YEAR, 2015);
			c.set(Calendar.MONTH, 11);
			c.set(Calendar.DAY_OF_MONTH, 1);
		}
		
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		start = c.getTime();

		while(start.before(end)){
			map.put(start, new TicketAccessSeries());
			c.setTime(start);
			c.set(Calendar.HOUR_OF_DAY,0);
			c.set(Calendar.MINUTE,0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);
			c.add(Calendar.DAY_OF_MONTH, 1);
			start = c.getTime();
		}
		
		for(TotAggregate a : access){
			Date d = a.getDate();
			c.setTime(d);
			c.set(Calendar.HOUR_OF_DAY,0);
			c.set(Calendar.MINUTE,0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);

			d = c.getTime();
			TicketAccessSeries tAS = map.get(d);
			if(tAS!=null){
					tAS.addToTotAccesses(a.getTot());
					map.put(d, tAS);
			}
		}
		
		for(TotAggregate t : ticket){
			Date d = t.getDate();
			c.setTime(d);
			c.set(Calendar.HOUR_OF_DAY,0);
			c.set(Calendar.MINUTE,0);
			c.set(Calendar.SECOND, 0);
			c.set(Calendar.MILLISECOND, 0);

			d = c.getTime();
			TicketAccessSeries tAS = map.get(d);
			if(tAS!=null){
				tAS.addToTotTickets(t.getTot());
				map.put(d, tAS);
			}
		}
		return map;
	}

	@Override
	public List<PoiRank> getPoiRank(Date start, Date end) {
		List<Poi> poiList = poiRepo.findAll();
		Map<String, String> namePoi = new HashMap<String, String>();
		for(Poi p : poiList){
			namePoi.put(p.getIdSite(), p.getName());
		}
		List<PoiRank> l = readRepo.getPoiRank(start, end);
		for(PoiRank p : l){
			p.setName(namePoi.get(p.getIdSite()));
		}
		return l;
	}


}
