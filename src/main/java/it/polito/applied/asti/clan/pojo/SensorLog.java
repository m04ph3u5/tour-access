package it.polito.applied.asti.clan.pojo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author andrea
 *
 */

@Document
public class SensorLog {
	

	@Id
	private String id;
	private String idSite; 		//CODICE_SITO
	private Date timestamp;     //UTC_VALUE
	private int idSonda; 	//NUMERO_SERIALE_SONDA
	private double valTemp;      //VALORE_GRADI_CENTIGRADI
	private double valHum;		//VALORE_UMIDITA
	private long timemarker;
	
	public String getIdSite() {
		return idSite;
	}
	public void setIdSite(String idSite) {
		this.idSite = idSite;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public int getIdSonda() {
		return idSonda;
	}
	public void setIdSonda(int idSonda) {
		this.idSonda = idSonda;
	}
	public double getValTemp() {
		return valTemp;
	}
	public void setValTemp(double valTemp) {
		this.valTemp = valTemp;
	}
	public double getValHum() {
		return valHum;
	}
	public void setValHum(double valHum) {
		this.valHum = valHum;
	}
	public String getId() {
		return id;
	}
	public long getTimemarker() {
		return timemarker;
	}
	public void setTimemarker(long timemarker) {
		this.timemarker = timemarker;
	}
	
	
	
	
}
