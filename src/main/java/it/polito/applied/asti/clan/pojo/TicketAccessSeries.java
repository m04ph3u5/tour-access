package it.polito.applied.asti.clan.pojo;


public class TicketAccessSeries {

	private long totTickets;
	private long totAccesses;
	
	public long getTotTickets() {
		return totTickets;
	}
	public void setTotTickets(long totTickets) {
		this.totTickets = totTickets;
	}
	public long getTotAccesses() {
		return totAccesses;
	}
	public void setTotAccesses(long totAccesses) {
		this.totAccesses = totAccesses;
	}
	public void addToTotTickets(long totTickets){
		this.totTickets+=totTickets;
	}
	public void addToTotAccesses(long totAccesses){
		this.totAccesses+=totAccesses;
	}
	
	
	
	
}
