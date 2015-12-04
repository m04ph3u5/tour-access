package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.Ticket;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class TicketRepositoryImpl implements CustomTicketRepository{

	/*
	status.released.id = s00001
	status.validated.id = s00002
	status.canceled.id = s00003
	 */
	@Value("${status.canceled.id}")
	private String CANCELED;
	@Value("${status.validated.id}")
	private String VALIDATED;
	@Value("${status.released.id}")
	private String RELEASED;
	
	/*
	role.dailyVisitor.id = r00001
	role.weeklyVisitor.id = r00002
	role.dailyVipVisitor.id = r00003
	role.weeklyVipVisitor.id = r00004
	role.service.id = r00005
	role.supervisor.id = r00006
	 */
	@Value("${role.dailyVisitor.id}")
	private String DAILY_VISITOR;
	@Value("${role.weeklyVisitor.id}")
	private String WEEKLY_VISITOR;
	@Value("${role.dailyVipVisitor.id}")
	private String DAILY_VIP_VISITOR;
	@Value("${role.weeklyVipVisitor.id}")
	private String WEEKLY_VIP_VISITOR;
	@Value("${role.service.id}")
	private String SERVICE;
	@Value("${role.supervisor.id}")
	private String SUPERVISOR;
	
	@Autowired
	private MongoOperations mongoOp;
	
	@Override
	public boolean isValid(String[] ticketNumbers, Date start) {
		Query q = new Query();
		Criteria c2 = new Criteria();
		c2.orOperator((Criteria.where("endDate").gte(start)), (Criteria.where("status").ne(RELEASED)) );
		
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
		q.addCriteria(Criteria.where("status").ne(CANCELED).
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
			myTicket = tList.get(0); //in prima posizione c'� sicuramente quello pi� recente
			if(myTicket.getStatus().equals(RELEASED)){
				Update u = new Update();
				u.set("startDate", d);
				
				Calendar c = Calendar.getInstance();
				Date endDate;
				c.setTime(d);
				
				if(myTicket.getRole().equals(DAILY_VISITOR)){
					c.set(Calendar.HOUR_OF_DAY,23);
					c.set(Calendar.MINUTE,59);
					c.set(Calendar.SECOND, 59);
					
				}
				else if(myTicket.getRole().equals(WEEKLY_VISITOR)){
					c.add(Calendar.DAY_OF_MONTH, 7);
					c.set(Calendar.HOUR_OF_DAY,23);
					c.set(Calendar.MINUTE,59);
					c.set(Calendar.SECOND, 59);
					
				}
				
				else if(myTicket.getRole().equals(DAILY_VIP_VISITOR)){
					
					c.set(Calendar.HOUR_OF_DAY,23);
					c.set(Calendar.MINUTE,59);
					c.set(Calendar.SECOND, 59);
					
				}
				else if(myTicket.getRole().equals(WEEKLY_VIP_VISITOR)){
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
