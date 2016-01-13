package it.polito.applied.asti.clan.pojo;


public class AppInfo {
	private long numAccess;
	private long numInstallation;
	private long numPathStarted;
	private long numCheckedTicket;
	

	public long getNumAccess() {
		return numAccess;
	}
	public void setNumAccess(long numAccess) {
		this.numAccess = numAccess;
	}
	public void addToNumAccess(long numAccess){
		this.numAccess+=numAccess;
	}
	public long getNumInstallation() {
		return numInstallation;
	}
	public void setNumInstallation(long numInstallation) {
		this.numInstallation = numInstallation;
	}
	public void addToNumInstallation(long numInstallation){
		this.numInstallation+=numInstallation;
	}
	public long getNumPathStarted() {
		return numPathStarted;
	}
	public void setNumPathStarted(long numPathStarted) {
		this.numPathStarted = numPathStarted;
	}
	public void addToNumPathStarted(long numPathStarted){
		this.numPathStarted+=numPathStarted;
	}
	public long getNumCheckedTicket() {
		return numCheckedTicket;
	}
	public void setNumCheckedTicket(long numCheckedTicket) {
		this.numCheckedTicket = numCheckedTicket;
	}
	public void addToNumCheckedTicket(long numCheckedTicket){
		this.numCheckedTicket+=numCheckedTicket;
	}
	
}
