package it.polito.applied.asti.clan.pojo;

public class SiteSensorDTO {

	private String idSite;
	private String name;
	private int numRead;
	private double avgTemperature;
	private double avgHumidity;
	
	
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
	public double getAvgTemperature() {
		return avgTemperature;
	}
	public void setAvgTemperature(double avgTemperature) {
		this.avgTemperature = avgTemperature;
	}
	public double getAvgHumidity() {
		return avgHumidity;
	}
	public void setAvgHumidity(double avgHumidity) {
		this.avgHumidity = avgHumidity;
	}
	public int getNumRead() {
		return numRead;
	}
	public void setNumRead(int numRead) {
		this.numRead = numRead;
	}
	
	
}
