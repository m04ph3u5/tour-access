package it.polito.applied.asti.clan.repository;

import java.util.Date;

import it.polito.applied.asti.clan.pojo.Read;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReadRepository extends MongoRepository<Read, String>, CustomReadRepository{
	
	public Read findById(String id);	

}
