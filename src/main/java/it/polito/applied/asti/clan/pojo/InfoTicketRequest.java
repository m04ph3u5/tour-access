package it.polito.applied.asti.clan.pojo;

public class InfoTicketRequest {
	
	private String gender;
	private String age;
	
	private Boolean withChildren;
	private Boolean withElderly;
	
	
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
	
	@Override
	public String toString(){
		return "Gender: "+gender+" Age: "+age+" withChildren: "+withChildren+" withElderly: "+withElderly;
	}
	
	

}
