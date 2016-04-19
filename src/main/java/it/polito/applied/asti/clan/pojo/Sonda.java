package it.polito.applied.asti.clan.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Sonda {

	@Id
	private String id;
	private int idSonda;
	private String name;
	private String idSite;
	
	
	public String getId() {
		return id;
	}
	public int getIdSonda() {
		return idSonda;
	}
	public void setIdSonda(int idSonda) {
		this.idSonda = idSonda;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdSite() {
		return idSite;
	}
	public void setIdSite(String idSite) {
		this.idSite = idSite;
	}
	
	
}
