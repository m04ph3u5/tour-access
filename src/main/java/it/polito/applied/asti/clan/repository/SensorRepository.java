package it.polito.applied.asti.clan.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.asti.clan.pojo.SensorLog;

public interface SensorRepository extends MongoRepository <SensorLog, String> {
		
}

