package it.polito.applied.asti.clan.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.pojo.Ticket;

@Service
public class UtilPostToAclTaskImpl implements UtilPostToAclTask {

	@Value("${url.sendTicket}")
	private String URLTOSEND;
	@Value("${url.ping}")
	private String URLTOPING;
	@Value("${url.deleteTicket}")
	private String URLTODELETE;

	
	public void sendTicketsToAcl(List<Ticket> tickets) throws JSONException, BadRequestException, IOException{

		if(tickets==null)
			throw new BadRequestException();
		
		JSONObject obj = new JSONObject();
		
		String[] idTickets = new String[tickets.size()];
		for(int i=0; i<tickets.size(); i++){
			idTickets[i] = tickets.get(i).getIdTicket();
		}
		Ticket t = tickets.get(0);
		
		obj.put("idTickets", idTickets);
		obj.put("role", t.getRole());
		if(t.getEmissionDate()!=null)
			obj.put("emissionDate", t.getEmissionDate().getTime());
		if(t.getStartDate()!=null)
			obj.put("startDate", t.getStartDate().getTime());
		if(t.getEndDate()!=null)	
			obj.put("endDate", t.getEndDate().getTime());
		obj.put("status", t.getStatus());
		if(t.getSites()!=null)
			obj.put("sites", t.getSites().toArray());
		obj.put("duration", t.getDuration());
		System.out.println(obj.toString());
		URL url = new URL(URLTOSEND);
		HttpsURLConnection httpCon = (HttpsURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("PUT");
		httpCon.setRequestProperty("Accept", "application/json");
		httpCon.setRequestProperty("Content-Type", "application/json");
		httpCon.setConnectTimeout(10000);
		OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
		out.write(obj.toString());
		out.flush();
		out.close();
		InputStream res = httpCon.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(res));
		String temp = null;
		StringBuilder sb = new StringBuilder();
		while((temp = in.readLine()) != null){
			sb.append(temp).append(" ");
		}
		String result = sb.toString();
		int response = httpCon.getResponseCode();
		if(response!=200 && response!=201)
			throw new BadRequestException();

		System.out.println(result);
		in.close();
	}


	@Override
	public void ping() throws BadRequestException, MalformedURLException,
			IOException {
		URL url = new URL(URLTOPING);
		HttpsURLConnection httpCon = (HttpsURLConnection) url.openConnection();
		httpCon.setRequestMethod("GET");
		httpCon.setConnectTimeout(10000);
		
		InputStream res = httpCon.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(res));
		String temp = null;
		StringBuilder sb = new StringBuilder();
		while((temp = in.readLine()) != null){
			sb.append(temp).append(" ");
		}
		String result = sb.toString();
		int response = httpCon.getResponseCode();
		if(response!=200)
			throw new BadRequestException(result);

		System.out.println(result);
		in.close();
	}


	@Override
	public void deleteTicketToAcl(Ticket ticket)
			throws JSONException, BadRequestException, MalformedURLException, IOException {
		if(ticket==null)
			throw new BadRequestException();
		
		JSONObject obj = new JSONObject();		
		obj.put("idTicket", ticket.getIdTicket());
		URL url = new URL(URLTODELETE);
		HttpsURLConnection httpCon = (HttpsURLConnection) url.openConnection();
		httpCon.setDoOutput(true);
		httpCon.setRequestMethod("PUT");
		httpCon.setRequestProperty("Accept", "application/json");
		httpCon.setRequestProperty("Content-Type", "application/json");
		httpCon.setConnectTimeout(10000);
		OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
		out.write(obj.toString());
		out.flush();
		out.close();
		InputStream res = httpCon.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(res));
		String temp = null;
		StringBuilder sb = new StringBuilder();
		while((temp = in.readLine()) != null){
			sb.append(temp).append(" ");
		}
		String result = sb.toString();
		int response = httpCon.getResponseCode();
		if(response!=200 && response!=201)
			throw new BadRequestException();

		System.out.println(result);
		in.close();
	}
	 
}
