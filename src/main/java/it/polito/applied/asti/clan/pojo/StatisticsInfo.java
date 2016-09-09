package it.polito.applied.asti.clan.pojo;


public class StatisticsInfo {
	private long totSingleTickets;
	private long totGroupTickets;
	private long totGroups;
	private long totTickets;
	private long totAccess;
	private long totMale;
	private long totFemale;
	private long young;
	private long middleAge;
	private long elderly;
	private long family;
	private long couple;
	private long schoolGroup;
	
	public StatisticsInfo(){
		
	}
	
	
	public long getTotTickets() {
		return totTickets;
	}
	public void setTotTickets(long totTickets) {
		this.totTickets = totTickets;
	}
	public long getTotSingleTickets() {
		return totSingleTickets;
	}
	public void setTotSingleTickets(long totSingleTickets) {
		this.totSingleTickets = totSingleTickets;
	}

	public long getTotAccess() {
		return totAccess;
	}

	public void setTotAccess(long totAccess) {
		this.totAccess = totAccess;
	}

	public long getTotGroupTickets() {
		return totGroupTickets;
	}

	public void setTotGroupTickets(long totGroupTickets) {
		this.totGroupTickets = totGroupTickets;
	}

	public long getTotGroups() {
		return totGroups;
	}

	public void setTotGroups(long totGroups) {
		this.totGroups = totGroups;
	}
	public long getTotMale() {
		return totMale;
	}

	public void setTotMale(long totMale) {
		this.totMale = totMale;
	}

	public long getTotFemale() {
		return totFemale;
	}

	public void setTotFemale(long totFemale) {
		this.totFemale = totFemale;
	}

	public long getYoung() {
		return young;
	}

	public void setYoung(long young) {
		this.young = young;
	}

	public long getMiddleAge() {
		return middleAge;
	}

	public void setMiddleAge(long middleAge) {
		this.middleAge = middleAge;
	}

	public long getElderly() {
		return elderly;
	}

	public void setElderly(long elderly) {
		this.elderly = elderly;
	}

	public long getFamily() {
		return family;
	}

	public void setFamily(long family) {
		this.family = family;
	}

	public long getCouple() {
		return couple;
	}

	public void setCouple(long couple) {
		this.couple = couple;
	}

	public long getSchoolGroup() {
		return schoolGroup;
	}

	public void setSchoolGroup(long schoolGroup) {
		this.schoolGroup = schoolGroup;
	}
	
}
