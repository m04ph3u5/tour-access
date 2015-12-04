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
	private Integer version;
	private Date date;
	private String deviceId;
	private LogType logType;
	
	/*ARGS*/
	private Integer idPath;
	private String idPoi;
	private Integer audioCurrentPos;
	private Integer imageGalleryPos;
	private Integer orientation;	// 0-landscape, 1-portrait
	private String ticketNumber; /*Numero biglietto*/
	private Integer videoCurrentPos;
	private Double[] position;
	private Boolean validPoiTicket;
	private String idComment;
	
	public Log(){
		
	}
	
	public Log(LogDTO log){
		this.source = "APP_ANDROID";
		this.version = log.getSource(); //source dall'applicazione mobile equivale al numero di versione
		this.date = log.getTimestamp();
		this.deviceId = log.getDevice_id();
		assignLogType(log.getType());
		this.idPath = log.getArgs().getId_path();
		this.idPoi = log.getArgs().getId_poi();
		this.audioCurrentPos = log.getArgs().getAudio_current_position();
		this.imageGalleryPos = log.getArgs().getImage_gallery_position();
		this.orientation = log.getArgs().getOrientation();
		this.ticketNumber = log.getArgs().getTicket_contents();
		this.videoCurrentPos = log.getArgs().getVideo_current_position();
		if(log.getArgs().getUser_location()!=null && log.getArgs().getUser_location().contains("|")){
			String[] pos = log.getArgs().getUser_location().split(Pattern.quote("|"));
			if(pos.length==2){
				this.position = new Double[2];
				this.position[0] = Double.parseDouble(pos[0]);
				this.position[1] = Double.parseDouble(pos[1]);
			}
		}else{
			System.out.println("POSITION MISS");
		}
		this.validPoiTicket = log.getArgs().isValid_poi_ticket();				
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
	public Double[] getPosition() {
		return position;
	}
	public void setPosition(Double[] position) {
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
