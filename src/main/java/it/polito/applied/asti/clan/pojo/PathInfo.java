package it.polito.applied.asti.clan.pojo;

import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class PathInfo {
	
	@Id
	private String id;
	
	@Indexed(unique=true)
	private int idPath;
	private float avgRating;
	private int numComments;
	private Map<Float, Integer> votes;	
	
	public int getIdPath() {
		return idPath;
	}
	public void setIdPath(int idPath) {
		this.idPath = idPath;
	}
	public float getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(float avgRating) {
		this.avgRating = avgRating;
	}
	public int getNumComments() {
		return numComments;
	}
	public void setNumComments(int numComments) {
		this.numComments = numComments;
	}
	public String getId() {
		return id;
	}
	public Map<Float, Integer> getVotes() {
		return votes;
	}
	public void setVotes(Map<Float, Integer> votes) {
		this.votes = votes;
	}
	public void incrementNumComments(){
		this.numComments++;
	}
		

}
