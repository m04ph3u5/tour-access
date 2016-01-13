package it.polito.applied.asti.clan.pojo;

public class AppAccessInstallSeries {
	private long totInstall;
	private long totAccesses;
	public long getTotInstall() {
		return totInstall;
	}
	public void setTotInstall(long totInstall) {
		this.totInstall = totInstall;
	}
	public long getTotAccesses() {
		return totAccesses;
	}
	public void setTotAccesses(long totAccesses) {
		this.totAccesses = totAccesses;
	}
	public void addToTotAccesses(long totAccesses){
		this.totAccesses+=totAccesses;
	}
	public void addToTotInstall(long totInstall){
		this.totInstall+=totInstall;
	}
	
}
