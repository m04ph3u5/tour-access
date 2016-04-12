package it.polito.applied.asti.clan.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.json.JSONException;

import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.pojo.SensorLog;
import it.polito.applied.asti.clan.pojo.Ticket;

public interface UtilPostToAclTask {

	public void sendTicketsToAcl(List<Ticket> tickets) throws JSONException, BadRequestException, MalformedURLException, IOException;
	public void ping() throws BadRequestException, MalformedURLException, IOException;
	
}
