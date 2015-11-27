package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.Ticket;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class TicketRepositoryImpl implements CustomTicketRepository{

	@Autowired
	private MongoOperations mongoOp;
	
	@Override
	public boolean isValid(String[] ticketNumbers, Date start, Date end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("idTicket").in(ticketNumbers)
				.andOperator(Criteria.where("endDate").gte(start)));
		
		long tot = mongoOp.count(q, Ticket.class);
		if(tot==0)
			return true;
		else
			return false;
	}

	@Override
	public Ticket findByTicketNumberNow(String ticketNumber) {
		Date d = new Date();
		Query q = new Query();
		q.addCriteria(Criteria.where("idTicket").is(ticketNumber)
				.andOperator(Criteria.where("startDate").gte(d)
				.andOperator(Criteria.where("endDate").lte(d))));
		return mongoOp.findOne(q, Ticket.class);
	}

	@Override
	public List<Ticket> getValidTickets() {
		Date d = new Date();
		Query q = new Query();
		q.addCriteria(Criteria.where("status").ne("CANCELED").
				andOperator(Criteria.where("endDate").gte(d)));
		q.fields().exclude("id");
		return mongoOp.find(q, Ticket.class);
	}

}
