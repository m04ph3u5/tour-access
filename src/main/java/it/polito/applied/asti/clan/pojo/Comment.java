package it.polito.applied.asti.clan.pojo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Comment {

	@Id
	private String id;
	private String idPath;
	private String idTicket;
	private String title;
	private String content;
	private String author;
	private float rating;
	private Date realdate;
	
	
	public String getIdPath() {
		return idPath;
	}
	public void setIdPath(String idPath) {
		this.idPath = idPath;
	}
	public String getIdTicket() {
		return idTicket;
	}
	public void setIdTicket(String idTicket) {
		this.idTicket = idTicket;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public Date getRealdate() {
		return realdate;
	}
	public void setRealdate(Date realdate) {
		this.realdate = realdate;
	}
	public String getId() {
		return id;
	}
	
	
}
