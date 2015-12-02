package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.Comment;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String>, CustomCommentRepository{

	public Comment findById(String id);
	public List<Comment> findByIdPath(String idPath);
}
