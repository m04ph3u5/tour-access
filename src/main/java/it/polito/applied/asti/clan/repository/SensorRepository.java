package it.polito.applied.asti.clan.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.asti.clan.pojo.SensorLog;
import it.polito.applied.asti.clan.pojo.TotAvgAggregate;

public interface SensorRepository extends MongoRepository <SensorLog, String>, CustomSensorRepository {

	public SensorLog findByTimemarker(long timemarker);

	
		
}

