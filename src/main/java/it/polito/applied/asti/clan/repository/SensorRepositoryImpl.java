package it.polito.applied.asti.clan.repository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;

import it.polito.applied.asti.clan.pojo.SensorLog;
import it.polito.applied.asti.clan.pojo.SiteSensorDTO;

public class SensorRepositoryImpl implements CustomSensorRepository {

	@Autowired
	private MongoOperations mongoOp;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getSites(){
		
		return mongoOp.getCollection("sensorLog").distinct("idSite");
	}

	@Override
	public List<SiteSensorDTO> getAvgs(Date start) {
		Criteria c = new Criteria();
		c = (Criteria.where("timestamp").gte(start)
				.andOperator(Criteria.where("timestamp").lte(new Date())));
		
		Aggregation agg = Aggregation.newAggregation(Aggregation.match(c), 
				Aggregation.group("idSite").avg("valTemp").as("avgTemperature")
					.avg("valHum").as("avgHumidity")
					.count().as("numRead"),
				Aggregation.project("avgTemperature", "avgHumidity", "numRead").and("idSite").previousOperation());
		
		AggregationResults result = mongoOp.aggregate(agg, SensorLog.class, SiteSensorDTO.class);
		
		List<SiteSensorDTO> l = result.getMappedResults();
		return l;
	}
}
