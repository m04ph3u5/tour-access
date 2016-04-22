package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.Log;
import it.polito.applied.asti.clan.pojo.LogType;
import it.polito.applied.asti.clan.pojo.Ticket;
import it.polito.applied.asti.clan.pojo.TotAggregate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class LogRepositoryImpl implements CustomLogRepository{

	@Autowired
	private MongoOperations mongoOp;
	
	@Override
	public Map<String, Long> getDateSeries(Date start, Date end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Long> getDeviceSeries(Date start, Date end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countNumberAccessFromDate(Date date) {
		Query q = new Query();
		q.addCriteria(Criteria.where("date").gte(date));
		return mongoOp.count(q, Log.class);
	}

	@Override
	public long countNumberInstallationFromDate(Date date) {
		Query q = new Query();
		q.addCriteria(Criteria.where("date").gte(date)
				.andOperator(Criteria.where("logType").is(LogType.Install)));
		return mongoOp.count(q, Log.class);
	}

	@Override
	public long distinctDevicesFromDate(Date date) {
	    DBObject q = new BasicDBObject("date", new BasicDBObject("$gte",date));
		List<?> l = mongoOp.getCollection("log").distinct("deviceId", q);
		if(l!=null)
			return l.size();
		else
			return 0;

	}

	@Override
	public boolean isDeviceInstalled(String deviceId) {
		Query q = new Query();
		q.addCriteria(Criteria.where("deviceId").is(deviceId)
				.andOperator(Criteria.where("logType").is(LogType.Install)));
		long l = mongoOp.count(q, Log.class);
		if(l>0)
			return true;
		else 
			return false;
	}

	@Override
	public List<TotAggregate> getAccessGrouped(Date start, Date end) {
		Criteria c = new Criteria();
		c = (Criteria.where("date").gte(start).
				andOperator(Criteria.where("date").lte(end)));
		
		
		Aggregation agg = Aggregation.newAggregation(Aggregation.match(c), 
				Aggregation.project()
				.andExpression("year(date)").as("year")
				.andExpression("month(date)").as("month")
				.andExpression("dayOfMonth(date)").as("day"),
				Aggregation.group("year","month", "day").count().as("tot"),
				Aggregation.sort(Sort.Direction.ASC, "year","month","day"));
		
		AggregationResults result = mongoOp.aggregate(agg, Log.class, TotAggregate.class);
		
		List<TotAggregate> l = result.getMappedResults();
		return l;
	}

	@Override
	public List<TotAggregate> getInstallGrouped(Date start, Date end) {
		Criteria c = new Criteria();
		c = (Criteria.where("date").gte(start).
				andOperator(Criteria.where("date").lte(end).
				andOperator(Criteria.where("logType").is(LogType.Install))));
		
		
		Aggregation agg = Aggregation.newAggregation(Aggregation.match(c), 
				Aggregation.project()
				.andExpression("year(date)").as("year")
				.andExpression("month(date)").as("month")
				.andExpression("dayOfMonth(date)").as("day"),
				Aggregation.group("year","month", "day").count().as("tot"),
				Aggregation.sort(Sort.Direction.ASC, "year","month","day"));
		
		AggregationResults result = mongoOp.aggregate(agg, Log.class, TotAggregate.class);
		
		List<TotAggregate> l = result.getMappedResults();
		return l;
	}

	@Override
	public List<TotAggregate> getPathStarted(Date start, Date end) {
		Criteria c = new Criteria();
		c = (Criteria.where("date").gte(start).
				andOperator(Criteria.where("date").lte(end).
				andOperator(Criteria.where("logType").is(LogType.OpenPath))));
		
		
		Aggregation agg = Aggregation.newAggregation(Aggregation.match(c), 
				Aggregation.project()
				.andExpression("year(date)").as("year")
				.andExpression("month(date)").as("month")
				.andExpression("dayOfMonth(date)").as("day"),
				Aggregation.group("year","month", "day").count().as("tot"),
				Aggregation.sort(Sort.Direction.ASC, "year","month","day"));
		
		AggregationResults result = mongoOp.aggregate(agg, Log.class, TotAggregate.class);
		
		List<TotAggregate> l = result.getMappedResults();
		return l;
	}

	@Override
	public List<TotAggregate> getCheckedTicket(Date start, Date end) {
		Criteria c = new Criteria();
		Criteria orC = new Criteria();
		orC.orOperator(Criteria.where("logType").is(LogType.CheckTicket), Criteria.where("logType").is(LogType.CheckTicketFEC));
		c = Criteria.where("date").gte(start).
				andOperator(Criteria.where("date").lte(end).
				andOperator(orC));
		
		Aggregation agg = Aggregation.newAggregation(Aggregation.match(c), 
				Aggregation.project()
				.andExpression("year(date)").as("year")
				.andExpression("month(date)").as("month")
				.andExpression("dayOfMonth(date)").as("day"),
				Aggregation.group("year","month", "day").count().as("tot"),
				Aggregation.sort(Sort.Direction.ASC, "year","month","day"));
		
		AggregationResults result = mongoOp.aggregate(agg, Log.class, TotAggregate.class);
		
		List<TotAggregate> l = result.getMappedResults();
		return l;
	}

}
