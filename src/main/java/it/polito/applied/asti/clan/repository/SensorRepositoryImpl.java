package it.polito.applied.asti.clan.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

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
	public List<TotAvgAggregate> getAvgSeriesTemperatureAndHumidity(String idSite, Date startDate, Date endDate, boolean hourGranualarity) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		long offset = cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET);
		
		Criteria c = new Criteria();
		c = Criteria.where("idSite").is(idSite)
				.andOperator(Criteria.where("timestamp").gte(startDate)
				.andOperator(Criteria.where("timestamp").lte(endDate)));
		Aggregation agg;
		
		
		if(!hourGranualarity){
			agg = Aggregation.newAggregation(Aggregation.match(c),
					Aggregation.project()
					.and("timestamp").plus(offset).as("timestamp")
					.and("idSonda").as("idSonda")
					.and("valTemp").as("valTemp")
					.and("valHum").as("valHum"),
					Aggregation.sort(Sort.Direction.DESC, "timestamp"),
					Aggregation.project()
					.andExpression("year(timestamp)").as("year")
					.andExpression("month(timestamp)").as("month")
					.andExpression("dayOfMonth(timestamp)").as("day")
					.and("valTemp").as("valTemp")
					.and("idSonda").as("idSonda")
					.and("valHum").as("valHum"),
					Aggregation.group(Aggregation.fields().and("year").and("month").and("day").and("idSonda"))
						.avg("valTemp").as("avgTemp")
						.avg("valHum").as("avgHum")
						.count().as("tot"));
		}else{
			agg = Aggregation.newAggregation(Aggregation.match(c),
					Aggregation.project()
					.and("timestamp").plus(offset).as("timestamp")
					.and("idSonda").as("idSonda")
					.and("valTemp").as("valTemp")
					.and("valHum").as("valHum"),
					Aggregation.sort(Sort.Direction.DESC, "timestamp"),
					Aggregation.project()
					.andExpression("year(timestamp)").as("year")
					.andExpression("month(timestamp)").as("month")
					.andExpression("dayOfMonth(timestamp)").as("day")
					.andExpression("hour(timestamp)").as("hour")
					.and("valTemp").as("valTemp")
					.and("idSonda").as("idSonda")
					.and("valHum").as("valHum"),
					Aggregation.group(Aggregation.fields().and("year").and("month").and("day").and("hour").and("idSonda"))
						.avg("valTemp").as("avgTemp")
						.avg("valHum").as("avgHum")
						.count().as("tot"));
			}
		
		AggregationResults result = mongoOp.aggregate(agg, SensorLog.class, TotAvgAggregate.class);
		List<TotAvgAggregate> i = result.getMappedResults();

//		if(i!=null){
//			System.out.println("START: "+startDate+" - END: "+endDate);
//			for(TotAvgAggregate tt : i){
//				System.out.println("AVG: "+tt.getAvgTemp()+" °C - "+tt.getAvgHum()+"% - DATE: "+tt.getYear()+"/"+tt.getMonth()+"/"+tt.getDay()+" "+tt.getHour() + " idSonda: " + tt.getIdSonda());
//			}
//		}
		return i;
	}

	/* (non-Javadoc)
	 * @see it.polito.applied.asti.clan.repository.CustomSensorRepository#find(java.util.Date, java.util.Date)
	 */
	@Override
	public List<SensorLog> find(Date start, Date end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("timestamp").lte(end)
				.andOperator(Criteria.where("timestamp").gte(start)));
		
		q.with(new Sort(Sort.Direction.ASC, "timestamp"));
		
		return mongoOp.find(q, SensorLog.class);
	}

}
