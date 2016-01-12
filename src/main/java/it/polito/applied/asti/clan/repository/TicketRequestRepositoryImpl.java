package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.TicketRequest;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class TicketRequestRepositoryImpl implements CustomTicketRequestRepository{
	
	@Autowired
	private MongoOperations mongoOp;
	
	@Override
	public long totalGroupTickets(Date start, Date end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("requestDate").gte(start)
				.andOperator(Criteria.where("requestDate").lte(end)
				.andOperator(Criteria.where("isGroup").is(true))));
		
		return mongoOp.count(q, TicketRequest.class);
		
	}

	@Override
	public long totalSingleTickets(Date start, Date end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("requestDate").gte(start)
				.andOperator(Criteria.where("requestDate").lte(end)
				.andOperator(Criteria.where("isGroup").is(false))));
		return mongoOp.count(q, TicketRequest.class);
	
	}

	@Override
	public long totalSingleManTickets(Date start, Date end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("requestDate").gte(start)
				.andOperator(Criteria.where("requestDate").lte(end)
				.andOperator(Criteria.where("info.gender").is("M"))));
		return mongoOp.count(q, TicketRequest.class);
	}

	@Override
	public long totalSingleWomanTickets(Date start, Date end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("requestDate").gte(start)
				.andOperator(Criteria.where("requestDate").lte(end)
				.andOperator(Criteria.where("info.gender").is("F"))));
		return mongoOp.count(q, TicketRequest.class);
	}

	@Override
	public long totalGroupWithChildrenTickets(Date start, Date end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("requestDate").gte(start)
				.andOperator(Criteria.where("requestDate").lte(end)
				.andOperator(Criteria.where("info.withChildren").is(true))));
		return mongoOp.count(q, TicketRequest.class);
	}

	@Override
	public long totalGroupWithOldManTickets(Date start, Date end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("requestDate").gte(start)
				.andOperator(Criteria.where("requestDate").lte(end)
				.andOperator(Criteria.where("info.withElderly").is(true))));
		return mongoOp.count(q, TicketRequest.class);
	}

	@Override
	public long totalSingleYoung(Date start, Date end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("requestDate").gte(start)
				.andOperator(Criteria.where("requestDate").lte(end)
				.andOperator(Criteria.where("age").is("young"))));
		return mongoOp.count(q, TicketRequest.class);
	}

	@Override
	public long totalSingleMiddleAge(Date start, Date end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("requestDate").gte(start)
				.andOperator(Criteria.where("requestDate").lte(end)
				.andOperator(Criteria.where("age").is("middleAge"))));
		return mongoOp.count(q, TicketRequest.class);
	}

	@Override
	public long totalSingleElderly(Date start, Date end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("requestDate").gte(start)
				.andOperator(Criteria.where("requestDate").lte(end)
				.andOperator(Criteria.where("age").is("elderly"))));
		return mongoOp.count(q, TicketRequest.class);
	}

}
