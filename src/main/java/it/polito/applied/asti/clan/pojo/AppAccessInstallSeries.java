package it.polito.applied.asti.clan.pojo;

public class AppAccessInstallSeries {
	private long totInstall;
	private long totDevice;
	public long getTotInstall() {
		return totInstall;
	}
	public void setTotInstall(long totInstall) {
		this.totInstall = totInstall;
	}
	public long getTotDevice() {
		return totDevice;
	}
	public void setTotDevice(long totDevice) {
		this.totDevice = totDevice;
	}
	public void addToTotDevice(long totDevice){
		this.totDevice+=totDevice;
	}
	public void addToTotInstall(long totInstall){
		this.totInstall+=totInstall;
	}
	
}
