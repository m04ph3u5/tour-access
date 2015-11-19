package it.polito.applied.asti.clan.controller;

import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.exception.NotFoundException;
import it.polito.applied.asti.clan.pojo.Name;
import it.polito.applied.asti.clan.pojo.Place;
import it.polito.applied.asti.clan.repository.PlaceRepository;
import it.polito.applied.asti.clan.repository.PlaceRepositoryImpl;
import it.polito.applied.asti.clan.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiRestController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PlaceRepository placeRepo;
	
	@RequestMapping(value="/v1/name", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public Name getNameOfUser(@RequestParam(value = "username", required=false) String username) throws BadRequestException, NotFoundException{
		if(username==null || username.equals(""))
			throw new BadRequestException();
		return userService.getNameByUserName(username);
	}
	
	@PreAuthorize("hasRole('ROLE_OPERATOR')")
	@RequestMapping(value="/v1/places", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<Place> getPlaces() {
		return placeRepo.findAll();
	}

}
