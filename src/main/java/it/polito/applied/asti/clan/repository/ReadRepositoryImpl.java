package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.Read;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class ReadRepositoryImpl implements CustomReadRepository{

	@Autowired
	private MongoOperations mongoOp;
	
	@Override
	public long countIngressFromDate(Date date) {
		Query q = new Query();
		q.addCriteria(Criteria.where("isAccepted").is(true)
				.andOperator(Criteria.where("dtaTransit").gte(date)));
		
		return mongoOp.count(q, Read.class);
	}

}
