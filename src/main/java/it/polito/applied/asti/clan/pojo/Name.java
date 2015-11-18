package it.polito.applied.asti.clan.pojo;

public class Name {
	
	private String name;
	
	public Name(){
		
	}
	
	public Name(String firstname, String lastname){
		name = firstname+" "+lastname;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}

}
