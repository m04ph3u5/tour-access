/**
 * 
 */
package it.polito.applied.asti.clan.service;

import java.util.Collections;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;

import it.polito.applied.asti.clan.artacom.wsdl.CaricaListaEventi;
import it.polito.applied.asti.clan.artacom.wsdl.CaricaListaEventiReq;
import it.polito.applied.asti.clan.artacom.wsdl.CaricaListaEventiResp;
import it.polito.applied.asti.clan.artacom.wsdl.CaricaListaEventiResponse;
import it.polito.applied.asti.clan.artacom.wsdl.DatiGeneraliEvento;
import it.polito.applied.asti.clan.artacom.wsdl.ObjectFactory;

/**
 * @author m04ph3u5
 *
 */
public class ArtacomClientService extends WebServiceGatewaySupport{

	private String userId;
	private String password;
	private String termId;
	private List<DatiGeneraliEvento> eventsList;
	
	/* (non-Javadoc)
	 * @see it.polito.applied.asti.clan.service.ArtacomClientService#getEventsList()
	 */
	
	public ArtacomClientService(String userId, String password, String termId){
		this.userId = userId;
		this.password = password;
		this.termId = termId;
		eventsList = loadEventsList();
	}
	
	public List<DatiGeneraliEvento> loadEventsList() {
		
		CaricaListaEventi request = new CaricaListaEventi();
		CaricaListaEventiReq requestBody = new CaricaListaEventiReq();
		requestBody.setCassa(termId);
		requestBody.setPassword(password);
		requestBody.setUtente(userId);
		request.setReq(requestBody);
	
		JAXBElement<CaricaListaEventi> wrappedRequest = new ObjectFactory().createCaricaListaEventi(request);

		@SuppressWarnings("unchecked")
		CaricaListaEventiResponse response = ((JAXBElement<CaricaListaEventiResponse>) getWebServiceTemplate()
				.marshalSendAndReceive(wrappedRequest)).getValue();
		if(response==null)
			return Collections.emptyList();
		CaricaListaEventiResp r = response.getCaricaListaEventiResult();
		if(r==null)
			return Collections.emptyList();
		return r.getEventi();
	}
	
	public List<DatiGeneraliEvento> getEventsList(){
		return eventsList;
	}

}
