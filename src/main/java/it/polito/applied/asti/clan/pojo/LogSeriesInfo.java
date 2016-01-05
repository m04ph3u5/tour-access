package it.polito.applied.asti.clan.pojo;

import java.util.Map;

public class LogSeriesInfo {
	private Map<String, Long> dateSeries;
	private Map<String, Long> deviceSeries;
	
	public LogSeriesInfo(){
		
	}
	
	public LogSeriesInfo(Map<String, Long> dateSeries, Map<String, Long> deviceSeries){
		this.dateSeries = dateSeries;
		this.deviceSeries = deviceSeries;
	}
	
	public Map<String, Long> getDateSeries() {
		return dateSeries;
	}
	public void setDateSeries(Map<String, Long> dateSeries) {
		this.dateSeries = dateSeries;
	}
	public Map<String, Long> getDeviceSeries() {
		return deviceSeries;
	}
	public void setDeviceSeries(Map<String, Long> deviceSeries) {
		this.deviceSeries = deviceSeries;
	}
	
	
}
