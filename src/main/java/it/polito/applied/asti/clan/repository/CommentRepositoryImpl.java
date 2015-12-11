package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class CommentRepositoryImpl implements CustomCommentRepository{

	@Autowired
	private MongoOperations mongoOp;
	
	@Override
	public Comment setComment(Comment c) {
		Query q = new Query();
		q.addCriteria(Criteria.where("idPath").is(c.getIdPath())
				.andOperator(Criteria.where("deviceId").is(c.getDeviceId())));
		
		Update u = new Update();
		u.set("idPath", c.getIdPath());
		u.set("idTicket", c.getIdTicket());
		u.set("content", c.getContent());
		u.set("author", c.getAuthor());
		u.set("deviceId", c.getDeviceId());
		u.set("title", c.getTitle());
		u.set("rating", c.getRating());
		u.set("realdate", c.getRealdate());
		FindAndModifyOptions options = new FindAndModifyOptions();
		options.upsert(true);
		options.returnNew(true);
		return mongoOp.findAndModify(q, u, options, Comment.class);
		
	}

}
