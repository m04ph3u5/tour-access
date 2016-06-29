/**
 * 
 */
package it.polito.applied.asti.clan.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.polito.applied.asti.clan.artacom.wsdl.CaricaListaEventiResp;
import it.polito.applied.asti.clan.artacom.wsdl.CaricaListaEventiResponse;
import it.polito.applied.asti.clan.artacom.wsdl.DatiGeneraliEvento;
import it.polito.applied.asti.clan.service.ArtacomClientService;

/**
 * @author m04ph3u5
 *
 */
@RestController
@RequestMapping(value="/v1/wsdl/")
public class ArtacomTestController {
	
	@Autowired
	private ArtacomClientService client;

	@RequestMapping(value="events", method=RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK)
	public List<DatiGeneraliEvento> getEventsList() {
		CaricaListaEventiResponse response = client.getEventsList();
		if(response==null)
			return Collections.emptyList();
		CaricaListaEventiResp events = response.getCaricaListaEventiResult();
		if(events==null)
			return Collections.emptyList();
		
		return events.getEventi();

	}
}
