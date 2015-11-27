package it.polito.applied.asti.clan.pojo;

public class LatLng {

	private double lat;
	private double lng;
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}
	public void setLng(double lng) {
		this.lng = lng;
	}
	public String getLatLng(){
		return ""+lat+", "+lng;
	}
	
}
