package it.polito.applied.asti.clan.controller;

import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.exception.NotFoundException;
import it.polito.applied.asti.clan.pojo.Credential;
import it.polito.applied.asti.clan.pojo.Name;
import it.polito.applied.asti.clan.pojo.Poi;
import it.polito.applied.asti.clan.pojo.PoiToSell;
import it.polito.applied.asti.clan.pojo.Read;
import it.polito.applied.asti.clan.pojo.Ticket;
import it.polito.applied.asti.clan.pojo.TicketRequestDTO;
import it.polito.applied.asti.clan.pojo.User;
import it.polito.applied.asti.clan.repository.PoiRepository;
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
	
	@RequestMapping(value="/v1/name", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Name getNameOfUser(@RequestParam(value = "username", required=false) String username) throws BadRequestException, NotFoundException{
		if(username==null || username.equals(""))
			throw new BadRequestException();
		return userService.getNameByUserName(username);
	}
	
	@RequestMapping(value="/v1/login", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void valdateCredentials(@RequestBody Credential credential) throws BadRequestException{
		boolean u = userService.validateCredential(credential);
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
	@RequestMapping(value="/v1/tickets", method=RequestMethod.POST)
	@ResponseStatus(value = HttpStatus.OK)
	public void postTickets(@RequestBody @Valid TicketRequestDTO ticketRequestDTO, BindingResult result, @AuthenticationPrincipal User u) throws BadRequestException {
		if(result.hasErrors())
			throw new BadRequestException();
		
		ticketService.operatorGenerateTickets(ticketRequestDTO, u.getId());
	}
	
	@PreAuthorize("hasRole('ROLE_OPERATOR')")
	@RequestMapping(value="/v1/accessiblePlaces", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<String> accessiblePlaces(@RequestParam(value="ticketNumber", required=true) String ticketNumber) throws BadRequestException {
		if(ticketNumber==null || ticketNumber.isEmpty())
			throw new BadRequestException();
		return ticketService.accessiblePlaces(ticketNumber);
	}
	
	//@PreAuthorize("hasRole('ROLE_ACCESSCONTROL')")
	@RequestMapping(value="/v1/tickets", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<Ticket> getValidTickets() throws BadRequestException {
		
		return ticketService.getValidTickets();
	}

	//@PreAuthorize("hasRole('ROLE_ACCESSCONTROL')")
	@RequestMapping(value="/v1/ticket", method=RequestMethod.PUT)
	@ResponseStatus(value = HttpStatus.OK)
	public void passingAttempt(@RequestBody @Valid Read read, BindingResult result) throws BadRequestException {
		 if(result.hasErrors())
			throw new BadRequestException();
		 read.setDateOnServer(new Date());
		 ticketService.savePassingAttempt(read);
	}
}
