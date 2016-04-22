package it.polito.applied.asti.clan.pojo;

public class TotAggregate {
	private long tot;
	private int year;
	private int month;
	private int day;
	public long getTot() {
		return tot;
	}
	public void setTot(long tot) {
		this.tot = tot;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	
	@Override
	public String toString(){
		return "Date: "+day+"/"+month+"/"+year+" - TOT:"+tot;
	}
}
