package it.polito.applied.asti.clan.pojo;

public class Args {
	
	private	int id_path;
	private	String id_poi;
	private int audio_current_position;
	private int image_gallery_position;
	private int orientation;	// 0-undefined, 1-portrait, 2-landscape
	private String ticket_contents; /*Numero biglietto*/
	private int video_current_position;
	private String user_location; // latitute+”|”+longitude
	private boolean valid_poi_ticket;		
	private CommentDTO comment;
	
	public int getId_path() {
		return id_path;
	}
	public void setId_path(int id_path) {
		this.id_path = id_path;
	}
	public String getId_poi() {
		return id_poi;
	}
	public void setId_poi(String id_poi) {
		this.id_poi = id_poi;
	}
	public int getAudio_current_position() {
		return audio_current_position;
	}
	public void setAudio_current_position(int audio_current_position) {
		this.audio_current_position = audio_current_position;
	}
	public int getImage_gallery_position() {
		return image_gallery_position;
	}
	public void setImage_gallery_position(int image_gallery_position) {
		this.image_gallery_position = image_gallery_position;
	}
	public int getOrientation() {
		return orientation;
	}
	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}
	public String getTicket_contents() {
		return ticket_contents;
	}
	public void setTicket_contents(String ticket_contents) {
		this.ticket_contents = ticket_contents;
	}
	public int getVideo_current_position() {
		return video_current_position;
	}
	public void setVideo_current_position(int video_current_position) {
		this.video_current_position = video_current_position;
	}
	public String getUser_location() {
		return user_location;
	}
	public void setUser_location(String user_location) {
		this.user_location = user_location;
	}
	public boolean isValid_poi_ticket() {
		return valid_poi_ticket;
	}
	public void setValid_poi_ticket(boolean valid_poi_ticket) {
		this.valid_poi_ticket = valid_poi_ticket;
	}
	public CommentDTO getComment() {
		return comment;
	}
	public void setComment(CommentDTO comment) {
		this.comment = comment;
	}
	
	
}
