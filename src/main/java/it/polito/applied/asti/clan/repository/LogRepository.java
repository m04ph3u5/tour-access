package it.polito.applied.asti.clan.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.asti.clan.pojo.Log;

public interface LogRepository extends MongoRepository<Log, String>, CustomLogRepository{


}
