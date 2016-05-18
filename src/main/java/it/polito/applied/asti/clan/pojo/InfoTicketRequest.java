package it.polito.applied.asti.clan.pojo;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class InfoTicketRequest {
	
	private String gender;
	private String age;
	
	private Boolean withChildren;
	private Boolean withElderly;
	
	private Boolean family;
	private Boolean couple;
	private Boolean schoolGroup;
	
	@Min(0)
	@Max(22)
	private int region;
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public Boolean isWithChildren() {
		return withChildren;
	}
	public void setWithChildren(Boolean withChildren) {
		this.withChildren = withChildren;
	}
	public Boolean isWithElderly() {
		return withElderly;
	}
	public void setWithElderly(Boolean withElderly) {
		this.withElderly = withElderly;
	}
	
	public Boolean getFamily() {
		return family;
	}
	public void setFamily(Boolean family) {
		this.family = family;
	}
	public Boolean getCouple() {
		return couple;
	}
	public void setCouple(Boolean couple) {
		this.couple = couple;
	}
	public Boolean getSchoolGroup() {
		return schoolGroup;
	}
	public void setSchoolGroup(Boolean schoolGroup) {
		this.schoolGroup = schoolGroup;
	}
	public int getRegion() {
		return region;
	}
	public void setRegion(int region) {
		this.region = region;
	}
	public Boolean getWithChildren() {
		return withChildren;
	}
	public Boolean getWithElderly() {
		return withElderly;
	}
	@Override
	public String toString(){
		return "Gender: "+gender+" Age: "+age+" withChildren: "+withChildren+" withElderly: "+withElderly+
				" familiy: "+family+" couple: "+couple+" schoolGroup: "+schoolGroup+" region: "+region;
	}
	
	

}
