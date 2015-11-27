package it.polito.applied.asti.clan.pojo;

public class Site {

	private String idSite;
	private String code;
	private String description;
	private String address;
	private String latLon;
	private String timetable;
	private boolean ticketable;
	private int type;
	
	public Site(Poi p){
		this.idSite = p.getIdSite();
		this.code = p.getName();
		this.description = p.getShortDescription();
		this.address = p.getAddress();
		this.latLon = p.getLatLng().getLatLng();
		this.timetable = p.getTimetable();
		this.ticketable = p.getTicketable();
		this.type = p.getType();
	}
	
	public String getIdSite() {
		return idSite;
	}
	public void setIdSite(String idSite) {
		this.idSite = idSite;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLatLon() {
		return latLon;
	}
	public void setLatLon(String latLon) {
		this.latLon = latLon;
	}
	public String getTimetable() {
		return timetable;
	}
	public void setTimetable(String timetable) {
		this.timetable = timetable;
	}
	public boolean isTicketable() {
		return ticketable;
	}
	public void setTicketable(boolean ticketable) {
		this.ticketable = ticketable;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
	
}
