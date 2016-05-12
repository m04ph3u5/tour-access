package it.polito.applied.asti.clan.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.asti.clan.pojo.Comment;

public interface CommentRepository extends MongoRepository<Comment, String>, CustomCommentRepository{

	public Comment findById(String id);
	public List<Comment> findByIdPathOrderByRealdateDesc(int idPath);
	public List<Comment> findByIdPath(int idPath);
}
