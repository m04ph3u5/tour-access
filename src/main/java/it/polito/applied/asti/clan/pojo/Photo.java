package it.polito.applied.asti.clan.pojo;

public class Photo {

	private String pathPhoto;
	private String title;
	private String description;
	private int type;
	/*	100 = personaggio
        101 = storia
        102 = curiosità
        103 = architettura
        104 = arte
    */
	
	public String getPathPhoto() {
		return pathPhoto;
	}
	public void setPathPhoto(String pathPhoto) {
		this.pathPhoto = pathPhoto;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	
	
}
