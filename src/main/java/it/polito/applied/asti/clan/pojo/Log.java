package it.polito.applied.asti.clan.pojo;

import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Log {
	
	@Id
	private String id;
	private String source;
	private int version;
	private Date date;
	private String deviceId;
	private LogType logType;
	
	/*ARGS*/
	private int idPath;
	private String idPoi;
	private int audioCurrentPos;
	private int imageGalleryPos;
	private int orientation;	// 0-landscape, 1-portrait
	private String ticketNumber; /*Numero biglietto*/
	private int videoCurrentPos;
	private double[] position;
	private boolean validPoiTicket;
	private String idComment;
	
	public Log(){
		
	}
	
	public Log(LogDTO log){
		this.source = "APP_ANDROID";
		this.version = log.getSource(); //source dall'applicazione mobile equivale al numero di versione
		this.date = log.getTimestamp();
		this.deviceId = log.getDevice_id();
		assignLogType(log.getType());
		this.idPath = log.getArgs().getID_PATH();
		this.idPoi = log.getArgs().getID_POI();
		this.audioCurrentPos = log.getArgs().getAUDIO_CURRENT_POSITION();
		this.imageGalleryPos = log.getArgs().getIMAGE_GALLERY_POSITION();
		this.orientation = log.getArgs().getORIENTATION();
		this.ticketNumber = log.getArgs().getTICKET_CONTENTS();
		this.videoCurrentPos = log.getArgs().getVIDEO_CURRENT_POSITION();
		if(log.getArgs().getUserLocation()!=null && log.getArgs().getUserLocation().contains("|")){
			String[] pos = log.getArgs().getUserLocation().split(Pattern.quote("|"));
			if(pos.length==2){
				this.position = new double[2];
				this.position[0] = Double.parseDouble(pos[0]);
				this.position[1] = Double.parseDouble(pos[1]);
			}
		}
		this.validPoiTicket = log.getArgs().isValidPoiTicket();				
	}
	
	public Log(LogDTO log, String idComment){
		this(log);
		this.idComment = idComment;
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public LogType getLogType() {
		return logType;
	}
	public void setLogType(LogType logType) {
		this.logType = logType;
	}
	public int getIdPath() {
		return idPath;
	}
	public void setIdPath(int idPath) {
		this.idPath = idPath;
	}
	public String getIdPoi() {
		return idPoi;
	}
	public void setIdPoi(String idPoi) {
		this.idPoi = idPoi;
	}
	public int getAudioCurrentPos() {
		return audioCurrentPos;
	}
	public void setAudioCurrentPos(int audioCurrentPos) {
		this.audioCurrentPos = audioCurrentPos;
	}
	public int getImageGalleryPos() {
		return imageGalleryPos;
	}
	public void setImageGalleryPos(int imageGalleryPos) {
		this.imageGalleryPos = imageGalleryPos;
	}
	public int getOrientation() {
		return orientation;
	}
	public void setOrientation(int orientation) {
		this.orientation = orientation;
	}
	public String getTicketNumber() {
		return ticketNumber;
	}
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}
	public int getVideoCurrentPos() {
		return videoCurrentPos;
	}
	public void setVideoCurrentPos(int videoCurrentPos) {
		this.videoCurrentPos = videoCurrentPos;
	}
	public double[] getPosition() {
		return position;
	}
	public void setPosition(double[] position) {
		this.position = position;
	}
	public boolean isValidPoiTicket() {
		return validPoiTicket;
	}
	public void setValidPoiTicket(boolean validPoiTicket) {
		this.validPoiTicket = validPoiTicket;
	}
	public String getId() {
		return id;
	}		
	public String getIdComment() {
		return idComment;
	}
	public void setIdComment(String idComment) {
		this.idComment = idComment;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}

	private void assignLogType(String type){
		if(type.equals("Open Application"))
			this.logType = LogType.OpenApp;
		else if(type.equals("Open PathActivity"))
			this.logType = LogType.OpenPath;
		else if(type.equals("Close PathActivity"))
			this.logType = LogType.ClosePath;
		else if(type.equals("Download finished"))
			this.logType = LogType.DownloadFinished;
		else if(type.equals("Unzip finished"))
			this.logType = LogType.UnzipFinished;
		else if(type.equals("Open PoiActivity"))
			this.logType = LogType.OpenPoi;
		else if(type.equals("Close PoiActivity"))
			this.logType = LogType.ClosePoi;
		else if(type.equals("Open AudioActivity"))
			this.logType = LogType.OpenAudio;
		else if(type.equals("Close AudioActivity"))
			this.logType = LogType.CloseAudio;
		else if(type.equals("Open PanoImageActivity"))
			this.logType = LogType.OpenPanoImage;
		else if(type.equals("Close PanoImageActivity"))
			this.logType = LogType.ClosePanoImage;
		else if(type.equals("Open VideoActivity"))
			this.logType = LogType.OpenVideo;
		else if(type.equals("Close VideoActivity"))
			this.logType = LogType.CloseVideo;
		else if(type.equals("DirectionRequest"))
			this.logType = LogType.DirectionRequest;
		else if(type.equals("Open GalleryItem"))
			this.logType = LogType.OpenGalleryItem;
		else if(type.equals("Close GalleryItem"))
			this.logType = LogType.CloseGalleryItem;
		else if(type.equals("CheckTicket for Extra Contents"))
			this.logType = LogType.CheckTicketFEC;
		else if(type.equals("CheckTicket"))
			this.logType = LogType.CheckTicket;
		else if(type.equals("Delete Path"))
			this.logType = LogType.DeletePath;
		else if(type.equals("comment"))
			this.logType = LogType.Comment;

	}
}
