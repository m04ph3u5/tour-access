package it.polito.applied.asti.clan.controller;

import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.exception.NotFoundException;
import it.polito.applied.asti.clan.exception.ServiceUnaivalableException;
import it.polito.applied.asti.clan.pojo.CommentsPage;
import it.polito.applied.asti.clan.pojo.CommentsRequest;
import it.polito.applied.asti.clan.pojo.Credential;
import it.polito.applied.asti.clan.pojo.GroupAggregateCount;
import it.polito.applied.asti.clan.pojo.LogDTO;
import it.polito.applied.asti.clan.pojo.Name;
import it.polito.applied.asti.clan.pojo.Poi;
import it.polito.applied.asti.clan.pojo.PoiToAC;
import it.polito.applied.asti.clan.pojo.PoiToSell;
import it.polito.applied.asti.clan.pojo.Read;
import it.polito.applied.asti.clan.pojo.Response;
import it.polito.applied.asti.clan.pojo.RoleTicket;
import it.polito.applied.asti.clan.pojo.StatisticsGroupsInfo;
import it.polito.applied.asti.clan.pojo.StatisticsInfo;
import it.polito.applied.asti.clan.pojo.StatisticsSinglesInfo;
import it.polito.applied.asti.clan.pojo.StatusTicket;
import it.polito.applied.asti.clan.pojo.Ticket;
import it.polito.applied.asti.clan.pojo.TicketNumber;
import it.polito.applied.asti.clan.pojo.TicketRequestDTO;
import it.polito.applied.asti.clan.pojo.User;
import it.polito.applied.asti.clan.pojo.VersionDTO;
import it.polito.applied.asti.clan.repository.PoiRepository;
import it.polito.applied.asti.clan.repository.TicketRepository;
import it.polito.applied.asti.clan.repository.TicketRequestRepository;
import it.polito.applied.asti.clan.service.AppService;
import it.polito.applied.asti.clan.service.TicketService;
import it.polito.applied.asti.clan.service.UserService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ApiRestController extends BaseController{

	@Autowired
	private UserService userService;

	@Autowired
	private PoiRepository placeRepo;

	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private TicketRepository ticketRepo;
	
	@Autowired
	private TicketRequestRepository ticketRequestRepo;

	@Autowired
	private AppService appService;
	

	@RequestMapping(value="/v1/name", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Name getNameOfUser(@RequestParam(value = "username", required=false) String username) throws BadRequestException, NotFoundException{
		if(username==null || username.equals(""))
			throw new BadRequestException();
		return userService.getNameByUserName(username);
	}

	@RequestMapping(value="/v1/loginOperator", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void validateCredentialsOperator(@RequestBody Credential credential) throws BadRequestException{
		boolean u = userService.validateCredential(credential, false);
		if(!u)
			throw new BadRequestException();
	}
	
	@RequestMapping(value="/v1/loginSupervisor", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void validateCredentialsSupervisor(@RequestBody Credential credential) throws BadRequestException{
		boolean u = userService.validateCredential(credential, true);
		if(!u)
			throw new BadRequestException();
	}

	@PreAuthorize("hasRole('ROLE_OPERATOR')")
	@RequestMapping(value="/v1/poiToSell", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<PoiToSell> getPoiToSell() {
		List<PoiToSell> poiToSell = new ArrayList<PoiToSell>();
		List<Poi> poiList = placeRepo.findAll();
		if(poiList!=null){
			for(Poi p : poiList){
				poiToSell.add(new PoiToSell(p));
			}
		}
		return poiToSell;
	}

	@PreAuthorize("hasRole('ROLE_OPERATOR')")
	@RequestMapping(value="/v1/buyTickets", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void postTickets(@RequestBody @Valid TicketRequestDTO ticketRequestDTO, BindingResult result, @AuthenticationPrincipal User u) throws BadRequestException, ServiceUnaivalableException {
		if(result.hasErrors())
			throw new BadRequestException();

		ticketService.operatorGenerateTickets(ticketRequestDTO, u.getId());
	}

	@PreAuthorize("hasRole('ROLE_OPERATOR')")
	@RequestMapping(value="/v1/pingService", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public void pingService() throws ServiceUnaivalableException {
		
		ticketService.pingService();
	}
	
	@PreAuthorize("hasRole('ROLE_OPERATOR')")
	@RequestMapping(value="/v1/accessiblePlaces", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<String> accessiblePlaces(@RequestParam(value="ticketNumber", required=true) String ticketNumber ) throws BadRequestException {
		if(ticketNumber==null || ticketNumber.isEmpty())
			throw new BadRequestException();
		return ticketService.accessiblePlaces(ticketNumber);
	}

	//preauthorize in security config
	@RequestMapping(value="/v1/places", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<PoiToAC> getAllPlaces() throws BadRequestException {

		List<Poi> poi =  ticketService.getAllPlaces();
		List<PoiToAC> poiToAC = new ArrayList<PoiToAC>();
		for(Poi p : poi){
			poiToAC.add(new PoiToAC(p));
		}
		return poiToAC;
	}

	//preauthorize in security config
	@RequestMapping(value="/v1/tickets", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<Ticket> getValidTickets() throws BadRequestException {

		return ticketService.getValidTickets();
	}

	//preauthorize in security config
	@RequestMapping(value="/v1/roles", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<RoleTicket> getRoles() throws BadRequestException {

		return ticketService.getRoles();
	}

	//preauthorize in security config
	@RequestMapping(value="/v1/states", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<StatusTicket> getStatus() throws BadRequestException {

		return ticketService.getStatus();
	}

	//preauthorize in security config
	@RequestMapping(value="/v1/ticket", method=RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public TicketNumber passingAttempt(@RequestBody @Valid Read read, BindingResult result) throws BadRequestException {
		if(result.hasErrors())
			throw new BadRequestException();
		read.setDateOnServer(new Date());
		ticketService.savePassingAttempt(read);
		TicketNumber t = new TicketNumber();
		t.setTicket(read.getIdTicket());
		return t;
	}

	@PreAuthorize("hasRole('ROLE_APP')")
	@RequestMapping(value="/v1/version", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public VersionDTO getVersion() throws BadRequestException {
		return new VersionDTO(appService.getVersion());
	}

	@PreAuthorize("hasRole('ROLE_APP')")
	@RequestMapping(value="/v1/checkTicket", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public Response checkTicket(@RequestBody TicketNumber t) throws BadRequestException {
		return new Response(appService.checkTicket(t.getTicket()));
	}

	@PreAuthorize("hasRole('ROLE_APP')")
	@RequestMapping(value="/v1/comments", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public CommentsPage getComments(@RequestBody CommentsRequest request) throws BadRequestException {
		return appService.getComments(request);
	}
	

	@PreAuthorize("hasRole('ROLE_APP')")
	@RequestMapping(value="/v1/logging", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void postLog(@RequestBody List<LogDTO> logs) throws BadRequestException, NotFoundException {
		List<LogDTO> logComment=null;
		for(int i=0; i<logs.size(); i++){
			LogDTO l = logs.get(i);
			if(l.getType().equals("comment")){
				if(logComment==null)
					logComment = new ArrayList<LogDTO>();
				logComment.add(logs.remove(i));
				i--;
			}
		}

		if(logComment!=null){
			appService.postComment(logComment);
		}

		if(logs.size()!=0)
			appService.postLog(logs);
	}
	
	
	@RequestMapping(value="/v1/statistics/totalTickets", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public StatisticsInfo getNumTotalTicketsInInterval( @RequestParam(value = "start", required=false) Date start, @RequestParam (value = "end", required=false) Date end) throws BadRequestException, NotFoundException{
		System.out.println("TEST");
		
		System.out.println(ticketRepo.totalTickets(start, end));
		long totGroup=0, totSingle=0;
		List<GroupAggregateCount> l = ticketRequestRepo.totalGroupTickets(start, end);
		for(GroupAggregateCount g : l){
			System.out.println(g);	
			if(g.isGroup() == true){
				totGroup = g.getTot();
			}else{
				totSingle = g.getTot();
			}
		}
			
		
		StatisticsInfo s = new StatisticsInfo (start, end, ticketRepo.totalTickets(start, end), totGroup,
				totSingle);
		return s;
	}
	
	@RequestMapping(value="/v1/statistics/groups", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public StatisticsGroupsInfo getGroupsStatistics( @RequestParam(value = "start", required=false) Date start, @RequestParam (value = "end", required=false) Date end) throws BadRequestException, NotFoundException{
		System.out.println("GROUPS STATISTICS");
		StatisticsGroupsInfo stats = new StatisticsGroupsInfo(0, 0);
		return stats;
		
	}
	@RequestMapping(value="/v1/statistics/singles", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public StatisticsSinglesInfo getSinglessStatistics( @RequestParam(value = "start", required=false) Date start, @RequestParam (value = "end", required=false) Date end) throws BadRequestException, NotFoundException{
		System.out.println("SINGLES STATISTICS");
		StatisticsSinglesInfo stats = new StatisticsSinglesInfo("","");
		return stats;
		
	}
}
