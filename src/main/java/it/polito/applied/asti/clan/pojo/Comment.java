package it.polito.applied.asti.clan.pojo;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@CompoundIndexes(value = 
{
    @CompoundIndex (name = "idPath_Date", def = "{'idPath': 1, 'realdate': -1}")
})
public class Comment {

	@Id
	private String id;
	
	@Indexed
	private int idPath;
	private String idTicket;
	private String deviceId;
	private String title;
	private String content;
	private String author;
	private float rating;
	private Date realdate;
	
	public Comment(){
		
	}
	
	public Comment(CommentDTO c, Date d, String deviceId){
		this.idPath = c.getIdPath();
		this.idTicket = c.getIdTicket();
		this.title = c.getTitle();
		this.content = c.getContent();
		this.author = c.getAuthor();
		this.rating = c.getRating();
		this.realdate = d;
		this.deviceId = deviceId;
	}
	
	
	public int getIdPath() {
		return idPath;
	}
	public void setIdPath(int idPath) {
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
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
