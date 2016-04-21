package it.polito.applied.asti.clan.pojo;

public class TotAvgAggregate {
	
	private double avgTemp;
	private double avgHum;
	private int year;
	private int month;
	private int day;
	private int hour;
	private int tot;
	private int idSonda;
	
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
	public int getTot() {
		return tot;
	}
	public void setTot(int tot) {
		this.tot = tot;
	}
	public double getAvgTemp() {
		return avgTemp;
	}
	public void setAvgTemp(double avgTemp) {
		this.avgTemp = avgTemp;
	}
	public double getAvgHum() {
		return avgHum;
	}
	public void setAvgHum(double avgHum) {
		this.avgHum = avgHum;
	}
	public int getHour() {
		return hour;
	}
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getIdSonda() {
		return idSonda;
	}
	public void setIdSonda(int idSonda) {
		this.idSonda = idSonda;
	}
	
	

}
