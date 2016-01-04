package it.polito.applied.asti.clan.pojo;

public class StatisticsGroupsInfo {
	private long withChildren;
	private long withElderly;
	
	
	
	public StatisticsGroupsInfo(long withChildren, long withElderly) {
		this.withChildren = withChildren;
		this.withElderly = withElderly;
	}
	public long getWithChildren() {
		return withChildren;
	}
	public void setWithChildren(long withChildren) {
		this.withChildren = withChildren;
	}
	public long getWithElderly() {
		return withElderly;
	}
	public void setWithElderly(long withElderly) {
		this.withElderly = withElderly;
	}
	
}
