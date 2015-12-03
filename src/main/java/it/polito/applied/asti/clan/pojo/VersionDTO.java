package it.polito.applied.asti.clan.pojo;

public class VersionDTO {

	private int version;
	
	public VersionDTO(){
		
	}
	
	public VersionDTO(VersionZip v){
		this.version = v.getVersion();
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	
}
