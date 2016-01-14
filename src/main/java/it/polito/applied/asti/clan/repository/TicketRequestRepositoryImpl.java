package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.Ticket;
import it.polito.applied.asti.clan.pojo.TicketRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class TicketRequestRepositoryImpl implements CustomTicketRequestRepository{
	
	@Autowired
	private MongoOperations mongoOp;
	
	//restituisce il numero di GRUPPI (NB non il numero di biglietti di gruppo)
	@Override
	public long totalGroups(Date start, Date end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("requestDate").gte(start)
				.andOperator(Criteria.where("requestDate").lte(end)
				.andOperator(Criteria.where("isGroup").is(true)
				.andOperator(Criteria.where("acceptedFromAcl").is(true)))));
		
		return mongoOp.count(q, TicketRequest.class);
		
	}

	@Override
	public long totalSingleTickets(Date start, Date end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("requestDate").gte(start)
				.andOperator(Criteria.where("requestDate").lte(end)
				.andOperator(Criteria.where("isGroup").is(false)
				.andOperator(Criteria.where("acceptedFromAcl").is(true)))));
				
		return mongoOp.count(q, TicketRequest.class);
	
	}

	@Override
	public long totalSingleManTickets(Date start, Date end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("requestDate").gte(start)
				.andOperator(Criteria.where("requestDate").lte(end)
				.andOperator(Criteria.where("info.gender").is("male")
				.andOperator(Criteria.where("acceptedFromAcl").is(true)))));
		return mongoOp.count(q, TicketRequest.class);
	}

	@Override
	public long totalSingleWomanTickets(Date start, Date end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("requestDate").gte(start)
				.andOperator(Criteria.where("requestDate").lte(end)
				.andOperator(Criteria.where("info.gender").is("female")
				.andOperator(Criteria.where("acceptedFromAcl").is(true)))));
		return mongoOp.count(q, TicketRequest.class);
	}

	@Override
	public long totalGroupWithChildrenTickets(Date start, Date end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("requestDate").gte(start)
				.andOperator(Criteria.where("requestDate").lte(end)
				.andOperator(Criteria.where("info.withChildren").is(true)
				.andOperator(Criteria.where("acceptedFromAcl").is(true)))));
		return mongoOp.count(q, TicketRequest.class);
	}

	@Override
	public long totalGroupWithOldManTickets(Date start, Date end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("requestDate").gte(start)
				.andOperator(Criteria.where("requestDate").lte(end)
				.andOperator(Criteria.where("info.withElderly").is(true)
						.andOperator(Criteria.where("acceptedFromAcl").is(true)))));
		return mongoOp.count(q, TicketRequest.class);
	}

	@Override
	public long totalSingleYoung(Date start, Date end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("requestDate").gte(start)
				.andOperator(Criteria.where("requestDate").lte(end)
				.andOperator(Criteria.where("info.age").is("young")
						.andOperator(Criteria.where("acceptedFromAcl").is(true)))));
		return mongoOp.count(q, TicketRequest.class);
	}

	@Override
	public long totalSingleMiddleAge(Date start, Date end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("requestDate").gte(start)
				.andOperator(Criteria.where("requestDate").lte(end)
				.andOperator(Criteria.where("info.age").is("middleAge").
				andOperator(Criteria.where("acceptedFromAcl").is(true)))));
		return mongoOp.count(q, TicketRequest.class);
	}

	@Override
	public long totalSingleElderly(Date start, Date end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("requestDate").gte(start)
				.andOperator(Criteria.where("requestDate").lte(end)
				.andOperator(Criteria.where("info.age").is("elderly")
				.andOperator(Criteria.where("acceptedFromAcl").is(true)))));
		return mongoOp.count(q, TicketRequest.class);
	}

	@Override
	public void toReleased(TicketRequest ticketRequest) {
		
		Query q = new Query();
		q.addCriteria(Criteria.where("id").is(ticketRequest.getId()));
		Update u = new Update();
		u.set("acceptedFromAcl", true);
		mongoOp.updateFirst(q, u, TicketRequest.class);
		
	}

}
