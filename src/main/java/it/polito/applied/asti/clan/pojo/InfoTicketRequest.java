package it.polito.applied.asti.clan.pojo;

public class InfoTicketRequest {
	
	private String gender;
	private String age;
	
	private boolean withChildren;
	private boolean withElderly;
	
	
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
	public boolean isWithChildren() {
		return withChildren;
	}
	public void setWithChildren(boolean withChildren) {
		this.withChildren = withChildren;
	}
	public boolean isWithElderly() {
		return withElderly;
	}
	public void setWithElderly(boolean withElderly) {
		this.withElderly = withElderly;
	}
	
	@Override
	public String toString(){
		return "Gender: "+gender+" Age: "+age+" withChildren: "+withChildren+" withElderly: "+withElderly;
	}
	
	

}
