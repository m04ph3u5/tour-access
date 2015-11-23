package it.polito.applied.asti.clan.pojo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Poi {

	@Id
	private String idPoi;
	private String name;
	
	private int type;
	/*	1 - museo
        2 - panorama
        3 - ristorazione
        4 - monumento religioso
        5 - parco
        6 - sito archeologico
        7 - edificio
    	8 - torre
    */
	private String imageId;
	private String shortDescription;
	private String markerImg;
	
	private double rating;
	private LatLng latLng;
	private String timetable;
	private String title;
	private String longDescription;
	private List<Photo> gallery;
	private List<Hotspot> hotspotList;
	private Resource audio;
	private Resource drone;
	private Pano pano;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getShortDescription() {
		return shortDescription;
	}
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}
	public String getMarkerImg() {
		return markerImg;
	}
	public void setMarkerImg(String markerImg) {
		this.markerImg = markerImg;
	}
	public double getRating() {
		return rating;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public LatLng getLatLng() {
		return latLng;
	}
	public void setLatLng(LatLng latLng) {
		this.latLng = latLng;
	}
	public String getTimetable() {
		return timetable;
	}
	public void setTimetable(String timetable) {
		this.timetable = timetable;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLongDescription() {
		return longDescription;
	}
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}
	public List<Photo> getGallery() {
		return gallery;
	}
	public void setGallery(List<Photo> gallery) {
		this.gallery = gallery;
	}
	public List<Hotspot> getHotspotList() {
		return hotspotList;
	}
	public void setHotspotList(List<Hotspot> hotspotList) {
		this.hotspotList = hotspotList;
	}
	public Resource getAudio() {
		return audio;
	}
	public void setAudio(Resource audio) {
		this.audio = audio;
	}
	public Resource getDrone() {
		return drone;
	}
	public void setDrone(Resource drone) {
		this.drone = drone;
	}
	public Pano getPano() {
		return pano;
	}
	public void setPano(Pano pano) {
		this.pano = pano;
	}
	public String getIdPoi() {
		return idPoi;
	}
	
	
	
	
}
