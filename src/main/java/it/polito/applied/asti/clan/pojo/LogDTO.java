package it.polito.applied.asti.clan.pojo;

import java.util.Date;

public class LogDTO {

	private int source;  //01 Android - 02 iOS
	private Date timestamp; /*UTC*/
	private String device_id;
	private String type;
	private Args args;
	
	public LogDTO(){
		
	}
	
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Args getArgs() {
		return args;
	}
	public void setArgs(Args args) {
		this.args = args;
	}
	
//	@Override
//	public String toString(){
//		String s= "Version: "+source;
//		s+=" Date: "+timestamp;
//		s+=" DeviceId: "+device_id;
//		s+=" Type: "+type;
//		s+=" UserLocation: "+args.getUser_location();
//		return s;
//	}
	
}
