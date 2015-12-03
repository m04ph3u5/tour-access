package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.Ticket;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class TicketRepositoryImpl implements CustomTicketRepository{

	@Autowired
	private MongoOperations mongoOp;
	
	@Override
	public boolean isValid(String[] ticketNumbers, Date start) {
		Query q = new Query();
		Criteria c2 = new Criteria();
		c2.orOperator((Criteria.where("endDate").gte(start)), (Criteria.where("status").gte("RELEASED")) );
		
		q.addCriteria(Criteria.where("idTicket").in((Object[])ticketNumbers)
				.andOperator(c2));
			
		
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

	@Override
	public void passingAccepted(String ticketNumber, Date d) {
		
		Ticket myTicket;
		Query q = new Query();
		q.addCriteria(Criteria.where("idTicket").is(ticketNumber));
		List<Ticket> tList = mongoOp.find(q, Ticket.class);
		if(tList!=null && tList.size()>0){
			myTicket = tList.get(0); //in prima posizione c'è sicuramente quello più recente
			if(myTicket.getStatus().equals("RELEASED")){
				Update u = new Update();
				u.set("startDate", d);
				
				Calendar c = Calendar.getInstance();
				Date endDate;
				c.setTime(d);
				
				if(myTicket.getRole().equals("DAILY_VISITOR")){
					c.set(Calendar.HOUR_OF_DAY,23);
					c.set(Calendar.MINUTE,59);
					c.set(Calendar.SECOND, 59);
					
				}
				else if(myTicket.getRole().equals("WEEKLY_VISITOR")){
					c.add(Calendar.DAY_OF_MONTH, 7);
					c.set(Calendar.HOUR_OF_DAY,23);
					c.set(Calendar.MINUTE,59);
					c.set(Calendar.SECOND, 59);
					
				}
				
				else if(myTicket.getRole().equals("DAILY_VIP_VISITOR")){
					
					c.set(Calendar.HOUR_OF_DAY,23);
					c.set(Calendar.MINUTE,59);
					c.set(Calendar.SECOND, 59);
					
				}
				else if(myTicket.getRole().equals("WEEKLY_VIP_VISITOR")){
					c.add(Calendar.DAY_OF_MONTH, 7);
					c.set(Calendar.HOUR_OF_DAY,23);
					c.set(Calendar.MINUTE,59);
					c.set(Calendar.SECOND, 59);
					
				}
				
				
				endDate = c.getTime();
				u.set("endDate", endDate);
				q.addCriteria(Criteria.where("id").is(myTicket.getId()));
				mongoOp.findAndModify(q, u, Ticket.class);
			}
		}
	
		
	}

}
