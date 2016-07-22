package it.polito.applied.asti.clan.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.asti.clan.pojo.Read;

public interface ReadRepository extends MongoRepository<Read, String>, CustomReadRepository{
	
	public Read findById(String id);

	/**
	 * @param from
	 * @return
	 */

}
