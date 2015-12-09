package it.polito.applied.asti.clan.pojo;

public class CommentBundle {
	
	private Comment comment;
	private boolean isNew;
	
	public CommentBundle(){
		
	}
	
	public CommentBundle(Comment comment, boolean isNew){
		this.comment = comment;
		this.isNew = isNew;
	}
	
	public Comment getComment() {
		return comment;
	}
	public void setComment(Comment comment) {
		this.comment = comment;
	}
	public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	
	
}
