package it.polito.applied.asti.clan.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

public class StatusTicketRepositoryImpl implements CustomStatusTicketRepository{

	@Autowired
	private MongoOperations mongoOp;
	
	
}
