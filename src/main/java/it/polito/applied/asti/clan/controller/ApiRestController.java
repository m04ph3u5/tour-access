package it.polito.applied.asti.clan.controller;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.exception.ConflictException;
import it.polito.applied.asti.clan.exception.NotFoundException;
import it.polito.applied.asti.clan.exception.ServiceUnaivalableException;
import it.polito.applied.asti.clan.pojo.AppAccessInstallSeries;
import it.polito.applied.asti.clan.pojo.AppInfo;
import it.polito.applied.asti.clan.pojo.CheckTicketInput;
import it.polito.applied.asti.clan.pojo.CommentsPage;
import it.polito.applied.asti.clan.pojo.CommentsRequest;
import it.polito.applied.asti.clan.pojo.Credential;
import it.polito.applied.asti.clan.pojo.DashboardInfo;
import it.polito.applied.asti.clan.pojo.InfoEnvironmentSite;
import it.polito.applied.asti.clan.pojo.LogDTO;
import it.polito.applied.asti.clan.pojo.LogSeriesInfo;
import it.polito.applied.asti.clan.pojo.Name;
import it.polito.applied.asti.clan.pojo.Poi;
import it.polito.applied.asti.clan.pojo.PoiRank;
import it.polito.applied.asti.clan.pojo.PoiToAC;
import it.polito.applied.asti.clan.pojo.PoiToSell;
import it.polito.applied.asti.clan.pojo.Read;
import it.polito.applied.asti.clan.pojo.Response;
import it.polito.applied.asti.clan.pojo.RoleTicket;
import it.polito.applied.asti.clan.pojo.SensorLog;
import it.polito.applied.asti.clan.pojo.StatisticsGroupsInfo;
import it.polito.applied.asti.clan.pojo.StatisticsInfo;
import it.polito.applied.asti.clan.pojo.StatisticsSinglesInfo;
import it.polito.applied.asti.clan.pojo.StatusTicket;
import it.polito.applied.asti.clan.pojo.Ticket;
import it.polito.applied.asti.clan.pojo.TicketAccessSeries;
import it.polito.applied.asti.clan.pojo.TicketNumber;
import it.polito.applied.asti.clan.pojo.TicketRequestDTO;
import it.polito.applied.asti.clan.pojo.TotAvgAggregate;
import it.polito.applied.asti.clan.pojo.User;
import it.polito.applied.asti.clan.pojo.VersionDTO;
import it.polito.applied.asti.clan.repository.PoiRepository;
import it.polito.applied.asti.clan.service.AppService;
import it.polito.applied.asti.clan.service.SensorService;
import it.polito.applied.asti.clan.service.TicketService;
import it.polito.applied.asti.clan.service.UserService;


@RestController
public class ApiRestController extends BaseController{

	@Autowired
	private UserService userService;

	@Autowired
	private PoiRepository placeRepo;

	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private SensorService sensorService;

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
				if(p.getTicketable())
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
	@RequestMapping(value="/v1/sensorLog", method=RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)		
	public void addSensorLog(@RequestBody SensorLog log) throws BadRequestException {
		
		sensorService.saveSensorLog(log);
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
	
	@RequestMapping(value="/v1/appVersion", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public void checkAppVersion(@RequestParam(value = "versionCode", required=true) Integer appVersion) throws BadRequestException, ConflictException {
		if(appVersion==null)
			appVersion = 0;
		appService.checkAppVersion(appVersion);
	}

	@PreAuthorize("hasRole('ROLE_APP')")
	@RequestMapping(value="/v1/checkTicket", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public Response checkTicket(@RequestBody CheckTicketInput t) throws BadRequestException {
		return new Response(appService.checkTicket(t));
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
	public void postLog(@RequestBody List<LogDTO> logs, @RequestParam(value = "versionCode", required=false) Integer appVersion) throws BadRequestException, NotFoundException {
		if(appVersion==null)
			appVersion = 0;
		
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
			appService.postComment(logComment, appVersion);
		}

		if(logs.size()!=0)
			appService.postLog(logs, appVersion);
	}
	
	@PreAuthorize("hasRole('ROLE_SUPERVISOR')")
	@RequestMapping(value="/v1/statistics/statisticsInfo", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public StatisticsInfo getNumTotalTicketsInInterval( @RequestParam(value = "start", required=true) String startTS, @RequestParam (value = "end", required=true) String endTS) throws BadRequestException, NotFoundException{
		Date start;
		Date end;
		Calendar c = Calendar.getInstance();

		c.setTimeInMillis(Long.parseLong(startTS));
		start = c.getTime();
		c.setTimeInMillis(Long.parseLong(endTS));
		end = c.getTime();

		return ticketService.getStatisticsInfo(start, end);
	}
	
	@PreAuthorize("hasRole('ROLE_SUPERVISOR')")
	@RequestMapping(value="/v1/statistics/groups", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public StatisticsGroupsInfo getGroupsStatistics( @RequestParam(value = "start", required=false) Date start, @RequestParam (value = "end", required=false) Date end) throws BadRequestException, NotFoundException{
		System.out.println("GROUPS STATISTICS");
		StatisticsGroupsInfo stats = new StatisticsGroupsInfo(0, 0);
		return stats;
		
	}
	
	@PreAuthorize("hasRole('ROLE_SUPERVISOR')")
	@RequestMapping(value="/v1/statistics/singles", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public StatisticsSinglesInfo getSinglessStatistics( @RequestParam(value = "start", required=false) Date start, @RequestParam (value = "end", required=false) Date end) throws BadRequestException, NotFoundException{
		System.out.println("SINGLES STATISTICS");
		StatisticsSinglesInfo stats = new StatisticsSinglesInfo("","");
		return stats;
		
	}
	
	@PreAuthorize("hasRole('ROLE_SUPERVISOR')")
	@RequestMapping(value="/v1/statistics/logApp", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public LogSeriesInfo getAppLogInfp( @RequestParam(value = "start", required=false) Date start, @RequestParam (value = "end", required=false) Date end) throws BadRequestException, NotFoundException{
		System.out.println("Log APP STATISTICS");
		return appService.getLogInfo(start, end);
		
	}
	
	@PreAuthorize("hasRole('ROLE_SUPERVISOR')")
	@RequestMapping(value="/v1/statistics/dashboardInfo", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public DashboardInfo getDashboardInfo(@RequestParam(value="start", required=false) String timestamp) throws BadRequestException, NotFoundException{
		Date date;
		Calendar c = Calendar.getInstance();

		if(timestamp==null || timestamp.isEmpty())
			date = new Date();
		else{
			c.setTimeInMillis(Long.parseLong(timestamp));
			date = c.getTime();
		}

		DashboardInfo d = new DashboardInfo();
		
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY,0);
		c.set(Calendar.MINUTE,0);
		c.set(Calendar.SECOND, 0);
		date = c.getTime();
		d.setTodayTicketSelled(ticketService.getNumberSelledTicket(date));
		d.setTodayIngress(ticketService.getNumberIngress(date));
		d.setTodayAppAccess(appService.getAccess(date));
		d.setTodayAppInstallation(appService.getInstallation(date));
		d.setTodayDevices(appService.getDevices(date));
		d.setMonitoredSites(sensorService.getMonitoredSiteInfo(date));
		return d;
	}
	
	@PreAuthorize("hasRole('ROLE_SUPERVISOR')")
	@RequestMapping(value="/v1/statistics/infoSite", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public InfoEnvironmentSite getInfoSite(@RequestParam(value="start", required=true) String start, @RequestParam(value="end", required=true) String end, @RequestParam(value="idSite", required=true) String idSite) throws BadRequestException, NotFoundException{
		
		if(start==null || start.isEmpty() || end==null || end.isEmpty())
			throw new BadRequestException();
		
		Date startDate, endDate;
		Calendar cStart = Calendar.getInstance();
		Calendar cEnd = Calendar.getInstance();
		
		cStart.setTimeInMillis(Long.parseLong(start));
		cStart.set(Calendar.HOUR_OF_DAY, 0);
		cStart.set(Calendar.MINUTE, 0);
		cStart.set(Calendar.SECOND, 0);
		cEnd.setTimeInMillis(Long.parseLong(end));
		cEnd.set(Calendar.HOUR_OF_DAY, 23);
		cEnd.set(Calendar.MINUTE, 59);
		cEnd.set(Calendar.SECOND, 59);
		
		startDate = cStart.getTime();
		endDate = cEnd.getTime();
		
		

		InfoEnvironmentSite info;
		
		info = sensorService.getInfoSite(startDate, endDate, idSite);
		System.out.println(info.getHumid());
		return info;
	}
	
	@PreAuthorize("hasRole('ROLE_SUPERVISOR')")
	@RequestMapping(value="/v1/statistics/statisticsSeries", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Map<Date,TicketAccessSeries> getStatisticsSeries(@RequestParam(value="start", required=true) String startTS, @RequestParam(value="end", required=true) String endTS) throws BadRequestException, NotFoundException{
		
		Date start;
		Date end;
		Calendar c = Calendar.getInstance();

		c.setTimeInMillis(Long.parseLong(startTS));
		start = c.getTime();
		c.setTimeInMillis(Long.parseLong(endTS));
		end = c.getTime();
		
		return ticketService.getTicketAccessSeries(start, end);
	}
	
	@PreAuthorize("hasRole('ROLE_SUPERVISOR')")
	@RequestMapping(value="/v1/statistics/poiRank", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<PoiRank> getPoiRank(@RequestParam(value="start", required=true) String startTS, @RequestParam(value="end", required=true) String endTS) throws BadRequestException, NotFoundException{
		
		Date start;
		Date end;
		Calendar c = Calendar.getInstance();

		c.setTimeInMillis(Long.parseLong(startTS));
		start = c.getTime();
		c.setTimeInMillis(Long.parseLong(endTS));
		end = c.getTime();
		
		return ticketService.getPoiRank(start, end);
	}
	
	@PreAuthorize("hasRole('ROLE_SUPERVISOR')")
	@RequestMapping(value="/v1/statistics/appSeries", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Map<Date,AppAccessInstallSeries> getAppSeries(@RequestParam(value="start", required=true) String startTS, @RequestParam(value="end", required=true) String endTS) throws BadRequestException, NotFoundException{
		
		Date start;
		Date end;
		Calendar c = Calendar.getInstance();

		c.setTimeInMillis(Long.parseLong(startTS));
		start = c.getTime();
		c.setTimeInMillis(Long.parseLong(endTS));
		end = c.getTime();
		
		return appService.getAppInstallAccessSeries(start, end);
	}
	
	@PreAuthorize("hasRole('ROLE_SUPERVISOR')")
	@RequestMapping(value="/v1/statistics/appInfo", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Map<Date,AppInfo> getAppInfo(@RequestParam(value="start", required=true) String startTS, @RequestParam(value="end", required=true) String endTS) throws BadRequestException, NotFoundException{
		
		Date start;
		Date end;
		Calendar c = Calendar.getInstance();

		c.setTimeInMillis(Long.parseLong(startTS));
		start = c.getTime();
		c.setTimeInMillis(Long.parseLong(endTS));
		end = c.getTime();
		
		return appService.getAppInfo(start, end);
	}
	
	@PreAuthorize("hasRole('ROLE_SUPERVISOR')")
	@RequestMapping(value="/v1/statistics/environmentInfo", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Map<Date,List<TotAvgAggregate>> getEnvironmentInfo(@RequestParam(value="start", required=true) String start, @RequestParam(value="end", required=true) String end,
			@RequestParam(value="idSite", required=true) String idSite) throws BadRequestException, NotFoundException{
		if(start==null || start.isEmpty() || end==null || end.isEmpty() || idSite==null || idSite.isEmpty())
			throw new BadRequestException();
		
		Date startDate, endDate;
		Calendar cStart = Calendar.getInstance();
		Calendar cEnd = Calendar.getInstance();
		
		cStart.setTimeInMillis(Long.parseLong(start));
		cStart.set(Calendar.HOUR_OF_DAY, 0);
		cStart.set(Calendar.MINUTE, 0);
		cStart.set(Calendar.SECOND, 0);
		cStart.set(Calendar.MILLISECOND, 0);

		cEnd.setTimeInMillis(Long.parseLong(end));
		cEnd.set(Calendar.HOUR_OF_DAY, 23);
		cEnd.set(Calendar.MINUTE, 59);
		cEnd.set(Calendar.SECOND, 59);
		cEnd.set(Calendar.MILLISECOND, 999);

		startDate = cStart.getTime();
		endDate = cEnd.getTime();
		
		return sensorService.getEnvironmentSeries(idSite, startDate, endDate);
	}

	
}
