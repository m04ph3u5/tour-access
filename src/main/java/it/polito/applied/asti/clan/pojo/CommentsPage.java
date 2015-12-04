package it.polito.applied.asti.clan.pojo;

import java.util.List;

public class CommentsPage {
	
	private int numComments;
	private float avgRating;
	private int nextPage;
	private List<CommentDTO> listOfComments;
	
	public int getNumComments() {
		return numComments;
	}
	public void setNumComments(int numComments) {
		this.numComments = numComments;
	}
	public float getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(float avgRating) {
		this.avgRating = avgRating;
	}
	public int getNextPage() {
		return nextPage;
	}
	public void setNextPage(int nextPage) {
		this.nextPage = nextPage;
	}
	public List<CommentDTO> getListOfComments() {
		return listOfComments;
	}
	public void setListOfComments(List<CommentDTO> listOfComments) {
		this.listOfComments = listOfComments;
	}
	
	

}
