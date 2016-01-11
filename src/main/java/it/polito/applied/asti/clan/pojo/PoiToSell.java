package it.polito.applied.asti.clan.pojo;

public class PoiToSell {
	
	private String id;
	private String name;
	private String imageId;
	private String timetable;
	
	public PoiToSell(){
		
	}
	
	public PoiToSell(Poi poi){
		this.id = poi.getIdSite();
		this.name = poi.getName();
		this.imageId = poi.getImageId();
		this.timetable = poi.getTimetable();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getTimetable() {
		return timetable;
	}

	public void setTimetable(String timetable) {
		this.timetable = timetable;
	}
	
	

}
