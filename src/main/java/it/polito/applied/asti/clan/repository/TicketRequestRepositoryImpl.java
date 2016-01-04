package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.GroupAggregateCount;
import it.polito.applied.asti.clan.pojo.TicketRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class TicketRequestRepositoryImpl implements CustomTicketRequestRepository{
	
	@Autowired
	private MongoOperations mongoOp;
	
	@Override
	public List <GroupAggregateCount> totalGroupTickets(Date start, Date end) {
		
		Criteria c = new Criteria();
		if(start != null && end != null)
			c = (Criteria.where("requestDate").gte(start).
					andOperator(Criteria.where("requestDate").lte(end)));
		else if(start == null && end != null)
			c = (Criteria.where("requestDate").lte(end));
		else if(start != null && end == null)
			c = (Criteria.where("requestDate").gte(start));
		
		
		Aggregation agg = Aggregation.newAggregation(Aggregation.group("isGroup").count().as("tot"), Aggregation.project("tot").and("isGroup").previousOperation());
		
		AggregationResults result = mongoOp.aggregate(agg, TicketRequest.class, GroupAggregateCount.class);
		
		List<GroupAggregateCount > l = result.getMappedResults();
		return l;
		
	}

	@Override
	public long totalSingleTickets(Date start, Date end) {
		Query q = new Query();
		if(start != null && end != null)
			q.addCriteria(Criteria.where("requestDate").gte(start).
					andOperator(Criteria.where("requestDate").lte(end)).andOperator(Criteria.where("isGroup").is(false)));
		else if(start == null && end != null)
			q.addCriteria(Criteria.where("requestDate").lte(end).andOperator(Criteria.where("isGroup").is(false)));
		else if(start != null && end == null)
			q.addCriteria(Criteria.where("requestDate").gte(start).andOperator(Criteria.where("isGroup").is(false)));
		else
			q.addCriteria(Criteria.where("isGroup").is(false));
		
		return mongoOp.count(q, TicketRequest.class);
	}

	@Override
	public long totalSingleManTickets(Date start, Date end) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long totalSingleWomanTickets(Date start, Date end) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long totalGroupWithChildrenTickets(Date start, Date end) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long totalGroupWithOldManTickets(Date start, Date end) {
		// TODO Auto-generated method stub
		return 0;
	}

}
