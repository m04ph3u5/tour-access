package it.polito.applied.asti.clan.pojo;

import java.util.List;

public class DashboardInfo {
	
	private long todayTicketSelled;
	private long todayIngress;
	private long todayPoi;
	private long todayDevices;
	private long todayAppInstallation;
	private List<SiteSensorDTO> monitoredSites;
	
	public long getTodayTicketSelled() {
		return todayTicketSelled;
	}
	public void setTodayTicketSelled(long todayTicketSelled) {
		this.todayTicketSelled = todayTicketSelled;
	}
	public long getTodayIngress() {
		return todayIngress;
	}
	public void setTodayIngress(long todayIngress) {
		this.todayIngress = todayIngress;
	}
	public long getTodayAppInstallation() {
		return todayAppInstallation;
	}
	public void setTodayAppInstallation(long todayAppInstallation) {
		this.todayAppInstallation = todayAppInstallation;
	}
	public long getTodayDevices() {
		return todayDevices;
	}
	public void setTodayDevices(long todayDevices) {
		this.todayDevices = todayDevices;
	}
	public List<SiteSensorDTO> getMonitoredSites() {
		return monitoredSites;
	}
	public void setMonitoredSites(List<SiteSensorDTO> monitoredSites) {
		this.monitoredSites = monitoredSites;
	}
	public long getTodayPoi() {
		return todayPoi;
	}
	public void setTodayPoi(long todayPoi) {
		this.todayPoi = todayPoi;
	}
	
	

}
