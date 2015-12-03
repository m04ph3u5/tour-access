package it.polito.applied.asti.clan.pojo;

public class Args {
	
	private	int ID_PATH;
	private	String ID_POI;
	private int AUDIO_CURRENT_POSITION;
	private int IMAGE_GALLERY_POSITION;
	private int ORIENTATION;	// 0-landscape, 1-portrait
	private String TICKET_CONTENTS; /*Numero biglietto*/
	private int VIDEO_CURRENT_POSITION;
	private String UserLocation; // latitute+”|”+longitude
	private boolean ValidPoiTicket;		
	private Comment COMMENT;
	
	public int getID_PATH() {
		return ID_PATH;
	}
	public void setID_PATH(int iD_PATH) {
		ID_PATH = iD_PATH;
	}
	public String getID_POI() {
		return ID_POI;
	}
	public void setID_POI(String iD_POI) {
		ID_POI = iD_POI;
	}
	public int getAUDIO_CURRENT_POSITION() {
		return AUDIO_CURRENT_POSITION;
	}
	public void setAUDIO_CURRENT_POSITION(int aUDIO_CURRENT_POSITION) {
		AUDIO_CURRENT_POSITION = aUDIO_CURRENT_POSITION;
	}
	public int getIMAGE_GALLERY_POSITION() {
		return IMAGE_GALLERY_POSITION;
	}
	public void setIMAGE_GALLERY_POSITION(int iMAGE_GALLERY_POSITION) {
		IMAGE_GALLERY_POSITION = iMAGE_GALLERY_POSITION;
	}
	public int getORIENTATION() {
		return ORIENTATION;
	}
	public void setORIENTATION(int oRIENTATION) {
		ORIENTATION = oRIENTATION;
	}
	public String getTICKET_CONTENTS() {
		return TICKET_CONTENTS;
	}
	public void setTICKET_CONTENTS(String tICKET_CONTENTS) {
		TICKET_CONTENTS = tICKET_CONTENTS;
	}
	public int getVIDEO_CURRENT_POSITION() {
		return VIDEO_CURRENT_POSITION;
	}
	public void setVIDEO_CURRENT_POSITION(int vIDEO_CURRENT_POSITION) {
		VIDEO_CURRENT_POSITION = vIDEO_CURRENT_POSITION;
	}
	public String getUserLocation() {
		return UserLocation;
	}
	public void setUserLocation(String userLocation) {
		UserLocation = userLocation;
	}
	public boolean isValidPoiTicket() {
		return ValidPoiTicket;
	}
	public void setValidPoiTicket(boolean validPoiTicket) {
		ValidPoiTicket = validPoiTicket;
	}
	public Comment getCOMMENT() {
		return COMMENT;
	}
	public void setCOMMENT(Comment cOMMENT) {
		COMMENT = cOMMENT;
	}	

	
}
