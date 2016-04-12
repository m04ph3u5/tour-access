package it.polito.applied.asti.clan.pojo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class DeviceTicketAssociation {
	
	@Id
	private String id;
	@Indexed(unique=true)
	private String deviceId;
	
	private List<TicketTime> tickets;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public List<TicketTime> getTickets() {
		return tickets;
	}

	public void setTickets(List<TicketTime> tickets) {
		this.tickets = tickets;
	}

	public String getId() {
		return id;
	}
	
	public void addTicketTime(TicketTime t){
		if(tickets==null)
			tickets = new ArrayList<TicketTime>();
		else
			tickets.add(t);
	}
	
	

}
