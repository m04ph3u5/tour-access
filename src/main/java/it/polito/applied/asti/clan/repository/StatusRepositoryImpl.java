package it.polito.applied.asti.clan.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class StatusRepositoryImpl implements CustomStatusRepository{

	@Autowired
	private MongoOperations mongoOp;
	
	
}
