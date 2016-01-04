package it.polito.applied.asti.clan.pojo;

public class GroupAggregateCount {
	private boolean isGroup;
	private long tot;
	
	
	public GroupAggregateCount(boolean isGroup, long tot) {
		this.isGroup = isGroup;
		this.tot = tot;
	}
	public boolean isGroup() {
		return isGroup;
	}
	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}
	public long getTot() {
		return tot;
	}
	public void setTot(long tot) {
		this.tot = tot;
	}
	
	@Override
	public String toString(){
		return "isGroup="+isGroup+" tot="+tot;
	}
	
}
