package it.polito.applied.asti.clan.repository;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.Fields;
import org.springframework.data.mongodb.core.query.Criteria;

import it.polito.applied.asti.clan.pojo.InfoEnvironmentSite;
import it.polito.applied.asti.clan.pojo.InfoEnvironmentSonda;
import it.polito.applied.asti.clan.pojo.SensorLog;
import it.polito.applied.asti.clan.pojo.SiteSensorDTO;
import it.polito.applied.asti.clan.pojo.TotAvgAggregate;

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

	@Override
	public List<InfoEnvironmentSonda> findInfoSiteInInterval(Date start, Date end, String idSite) {
		Criteria c = new Criteria();
		c = Criteria.where("idSite").is(idSite).andOperator(Criteria.where("timestamp").gte(start).andOperator(Criteria.where("timestamp").lte(end)));
		
		Aggregation agg = Aggregation.newAggregation(Aggregation.match(c), 
				Aggregation.group("idSonda").avg("valTemp").as("temp")
					.avg("valHum").as("humid")
					.count().as("numRilevazioni"),
				Aggregation.project("temp", "humid", "numRilevazioni").and("idSonda").previousOperation());
		AggregationResults result = mongoOp.aggregate(agg, SensorLog.class, InfoEnvironmentSonda.class);
		
		List<InfoEnvironmentSonda> i = result.getMappedResults();
		return i;
	}

	@Override
	public List<TotAvgAggregate> getAvgSeriesTemperature(String idSite, int idSonda, Date startDate, Date endDate) {
		Criteria c = new Criteria();
		c = Criteria.where("idSite").is(idSite)
				.andOperator(Criteria.where("idSonda").is(idSonda)
				.andOperator(Criteria.where("timestamp").gte(startDate)
				.andOperator(Criteria.where("timestamp").lte(endDate))));
		
		Aggregation agg = Aggregation.newAggregation(Aggregation.match(c),
				Aggregation.project().andExpression("year(timestamp)").as("year")
				.andExpression("month(timestamp)").as("month")
				.andExpression("dayOfMonth(timestamp)").as("day")
				.and("valTemp"),
				Aggregation.group(Aggregation.fields().and("year").and("month").and("day")).avg("valTemp").as("avg"),
				Aggregation.project("avg").and("year").previousOperation().and("month").previousOperation().and("day").previousOperation());
		
		AggregationResults result = mongoOp.aggregate(agg, SensorLog.class, TotAvgAggregate.class);
		List<TotAvgAggregate> i = result.getMappedResults();

		if(i!=null){
			for(TotAvgAggregate tt : i){
				System.out.println("AVG: "+tt.getAvg()+" DATE: "+tt.getYear()+"/"+tt.getMonth()+"/"+tt.getDay());
			}
		}
		return null;
	}

	@Override
	public List<TotAvgAggregate> getAvgSeriesHumidity(String idSite, int idSonda, Date startDate, Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}
}
