package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.Log;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<Log, String>, CustomLogRepository{


}
