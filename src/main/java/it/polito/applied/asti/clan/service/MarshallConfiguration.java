/**
 * 
 */
package it.polito.applied.asti.clan.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

/**
 * @author m04ph3u5
 *
 */
@Configuration
public class MarshallConfiguration {

	private final String wsdl ="it.polito.applied.asti.clan.artacom.wsdl";
	private final String urlService ="http://tickettest.artacom.it:80/biglietteria/services-ws/client/v1_1";
	private String userId="tonic";
	private String password="tonic001";
	private String termId="ws_tonic1";

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller= new Jaxb2Marshaller();
		marshaller.setContextPath(wsdl);
		return marshaller;
	}
	
	@Bean
	public ArtacomClientService webServiceClient(Jaxb2Marshaller marshaller) {
		ArtacomClientService client = new ArtacomClientService(userId, password, termId);
		client.setDefaultUri(urlService);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
}
