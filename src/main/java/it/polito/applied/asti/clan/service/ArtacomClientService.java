/**
 * 
 */
package it.polito.applied.asti.clan.service;

import javax.xml.bind.JAXBElement;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import it.polito.applied.asti.clan.artacom.wsdl.CaricaListaEventi;
import it.polito.applied.asti.clan.artacom.wsdl.CaricaListaEventiReq;
import it.polito.applied.asti.clan.artacom.wsdl.CaricaListaEventiResponse;
import it.polito.applied.asti.clan.artacom.wsdl.ObjectFactory;

/**
 * @author m04ph3u5
 *
 */
public class ArtacomClientService extends WebServiceGatewaySupport{

	/* (non-Javadoc)
	 * @see it.polito.applied.asti.clan.service.ArtacomClientService#getEventsList()
	 */
	
	private final String userId="tonic";
	private final String password="tonic001";
	private final String termId="ws_tonic1";
	
	public CaricaListaEventiResponse getEventsList() {
		
		CaricaListaEventi request = new CaricaListaEventi();
		CaricaListaEventiReq requestBody = new CaricaListaEventiReq();
		requestBody.setCassa(termId);
		requestBody.setPassword(password);
		requestBody.setUtente(userId);
		request.setReq(requestBody);
	
		JAXBElement<CaricaListaEventi> wrappedRequest = new ObjectFactory().createCaricaListaEventi(request);

		CaricaListaEventiResponse response = ((JAXBElement<CaricaListaEventiResponse>) getWebServiceTemplate()
				.marshalSendAndReceive(wrappedRequest)).getValue();
		return response;
	}

}
