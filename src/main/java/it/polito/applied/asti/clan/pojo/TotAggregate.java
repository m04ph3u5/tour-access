package it.polito.applied.asti.clan.pojo;

import java.util.Date;

public class TotAggregate {
	private Date date;
	private long tot;
	
	
	public TotAggregate(Date date, long tot) {
		this.date = date;
		this.tot = tot;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public long getTot() {
		return tot;
	}
	public void setTot(long tot) {
		this.tot = tot;
	}
	
	@Override
	public String toString(){
		return "date="+date+" tot="+tot;
	}
	
}
