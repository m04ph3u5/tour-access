package it.polito.applied.asti.clan.pojo;

import java.text.SimpleDateFormat;

public class CommentDTO {

	private String title;
	private String content;
	private String author;
	private float rating;
	private String date;
	private String time;
	
	public CommentDTO(){
		
	}
	
	public CommentDTO(Comment c){
		this.title = c.getTitle();
		this.content = c.getContent();
		this.author = c.getAuthor();
		this.rating = c.getRating();
		if(c.getRealdate()!=null){
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	        this.date = dateFormat.format(c.getRealdate());
	        dateFormat.applyLocalizedPattern("HH:mm");
	        this.time = dateFormat.format(c.getRealdate());
		}
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	
}
