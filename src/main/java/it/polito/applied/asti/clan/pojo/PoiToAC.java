package it.polito.applied.asti.clan.pojo;

public class PoiToAC {

	private String idSite;
	private String name;
	private String shortDescription;
	private String address;
	private String latLng;
	private String timetable;
	private boolean ticketable;
	private int type;
	
	public PoiToAC(){
		
	}
	
	public PoiToAC(Poi poi){
		this.idSite = poi.getIdSite();
		this.name = poi.getName();
		this.shortDescription = poi.getShortDescription();
		this.address = poi.getAddress();
		if(poi.getLatLng()!=null){
			this.latLng = poi.getLatLng().getLatLng();
		}
		this.timetable = poi.getTimetable();
		this.ticketable = poi.getTicketable();
		this.type = poi.getType();
	}
	
	public String getIdSite() {
		return idSite;
	}
	public void setIdSite(String idSite) {
		this.idSite = idSite;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLatLng() {
		return latLng;
	}
	public void setLatLng(String latLng) {
		this.latLng = latLng;
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
