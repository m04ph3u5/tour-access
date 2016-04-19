package it.polito.applied.asti.clan.pojo;

import java.util.ArrayList;
import java.util.List;

public class InfoEnvironmentSite {

	private int numRilevazioni;
	private int numSonde;
	private float temp;
	private float humid;
	private List<Sonda> sonde;
	
	
	public InfoEnvironmentSite() {
		super();
		this.sonde = new ArrayList<Sonda>();
	}
	
	public InfoEnvironmentSite(int numRilevazioni, int numSonde, float temp, float humid, List<Sonda> sonde) {
		super();
		this.numRilevazioni = numRilevazioni;
		this.numSonde = numSonde;
		this.temp = temp;
		this.humid = humid;
		this.sonde = sonde;
	}
	public void addSonda(Sonda idS){
		sonde.add(idS);
	}
	public List<Sonda> getSonde() {
		return sonde;
	}
	public void setSonde(List<Sonda> sonde) {
		this.sonde = sonde;
	}
	public int getNumRilevazioni() {
		return numRilevazioni;
	}
	public void setNumRilevazioni(int numRilevazioni) {
		this.numRilevazioni = numRilevazioni;
	}
	public int getNumSonde() {
		return numSonde;
	}
	public void setNumSonde(int numSonde) {
		this.numSonde = numSonde;
	}
	public float getTemp() {
		return temp;
	}
	public void setTemp(float temp) {
		this.temp = temp;
	}
	public float getHumid() {
		return humid;
	}
	public void setHumid(float humid) {
		this.humid = humid;
	}
	
	
}
