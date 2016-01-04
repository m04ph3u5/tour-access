package it.polito.applied.asti.clan.pojo;

import java.util.Date;

public class StatisticsInfo {
	private Date start;
	private Date end;
	private long totTickets;
	private long totGroups;
	private long totSingle;
	
	
	
	public StatisticsInfo(Date start, Date end, long totTickets, long totGroups,
			long totSingle) {
		this.start = start;
		this.end = end;
		this.totTickets = totTickets;
		this.totGroups = totGroups;
		this.totSingle = totSingle;
		
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public long getTotTickets() {
		return totTickets;
	}
	public void setTotTickets(long totTickets) {
		this.totTickets = totTickets;
	}
	public long getTotGroups() {
		return totGroups;
	}
	public void setTotGroups(long totGroups) {
		this.totGroups = totGroups;
	}
	public long getTotSingle() {
		return totSingle;
	}
	public void setTotSingle(long totSingle) {
		this.totSingle = totSingle;
	}

	
	
}
