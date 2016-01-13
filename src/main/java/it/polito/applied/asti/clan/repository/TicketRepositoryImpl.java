package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.Ticket;
import it.polito.applied.asti.clan.pojo.TotAggregate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
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
	@Value("${status.pending.id}")
	private String PENDING;
	
	/*
	role.dailyVisitor.id = r00001
	role.weeklyVisitor.id = r00002
	role.dailyVipVisitor.id = r00003
	role.weeklyVipVisitor.id = r00004
	role.service.id = r00005
	role.supervisor.id = r00006
	 */
	@Value("${role.dailyVisitor.id}")
	private int DAILY_VISITOR;
	@Value("${role.weeklyVisitor.id}")
	private int WEEKLY_VISITOR;
	@Value("${role.dailyVipVisitor.id}")
	private int DAILY_VIP_VISITOR;
	@Value("${role.weeklyVipVisitor.id}")
	private int WEEKLY_VIP_VISITOR;
	@Value("${role.service.id}")
	private int SERVICE;
	@Value("${role.supervisor.id}")
	private int SUPERVISOR;
	
	@Autowired
	private MongoOperations mongoOp;
	
	@Override
	public boolean isValid(String[] ticketNumbers, Date start) {
		Query q = new Query();
		Criteria c2 = new Criteria();
		
		c2.orOperator((Criteria.where("endDate").gte(start)), (Criteria.where("status").ne(PENDING)), (Criteria.where("status").ne(CANCELED)));
		
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
		q.fields().exclude("ticketRequestId");
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
				
				if(myTicket.getRole()==DAILY_VISITOR){
					c.set(Calendar.HOUR_OF_DAY,23);
					c.set(Calendar.MINUTE,59);
					c.set(Calendar.SECOND, 59);
					
				}
				else if(myTicket.getRole()==WEEKLY_VISITOR){
					c.add(Calendar.DAY_OF_MONTH, 7);
					c.set(Calendar.HOUR_OF_DAY,23);
					c.set(Calendar.MINUTE,59);
					c.set(Calendar.SECOND, 59);
					
				}
				
				else if(myTicket.getRole()==DAILY_VIP_VISITOR){
					
					c.set(Calendar.HOUR_OF_DAY,23);
					c.set(Calendar.MINUTE,59);
					c.set(Calendar.SECOND, 59);
					
				}
				else if(myTicket.getRole()==WEEKLY_VIP_VISITOR){
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

	@Override
	public void removeLastTickets(List<Ticket> tickets) {
		List<String> ids = new ArrayList<String>();
		for(Ticket t : tickets)
			ids.add(t.getIdTicket());
		Query q = new Query();
		q.addCriteria(Criteria.where("idTicket").in(ids)
				.andOperator(Criteria.where("status").is(PENDING)));
		mongoOp.remove(q, Ticket.class);
	}

	@Override
	public void toReleased(List<Ticket> tickets) {
		List<String> ids = new ArrayList<String>();
		for(Ticket t : tickets)
			ids.add(t.getIdTicket());
		Query q = new Query();
		q.addCriteria(Criteria.where("idTicket").in(ids)
				.andOperator(Criteria.where("status").is(PENDING)));
		Update u = new Update();
		u.set("status", RELEASED);
		mongoOp.updateMulti(q, u, Ticket.class);
	}

	@Override
	public long totalTickets(Date start, Date end) {
		Query q = new Query();
		if(start != null && end != null)
			q.addCriteria(Criteria.where("emissionDate").gte(start)
					.andOperator(Criteria.where("emissionDate").lte(end)
					.andOperator(Criteria.where("status").ne(PENDING))));
		else if(start == null && end != null)
			q.addCriteria(Criteria.where("emissionDate").lte(end).andOperator(Criteria.where("status").ne(PENDING)));
		else if(start != null && end == null)
			q.addCriteria(Criteria.where("emissionDate").gte(start).andOperator(Criteria.where("status").ne(PENDING)));
		return mongoOp.count(q, Ticket.class);
		
	}

	@Override
	public long countTicketFromDate(Date date) {
		Query q = new Query();
		q.addCriteria(Criteria.where("emissionDate").gte(date).andOperator(Criteria.where("status").ne(PENDING)));
		return mongoOp.count(q, Ticket.class);
	}
	
	@Override
	public List<TotAggregate> getTicketGrouped(Date start, Date end){
		Criteria c = new Criteria();
		c = (Criteria.where("emissionDate").gte(start).
				andOperator(Criteria.where("emissionDate").lte(end).andOperator(Criteria.where("status").ne(PENDING))));
		
		
		Aggregation agg = Aggregation.newAggregation(Aggregation.match(c), Aggregation.group("emissionDate").count().as("tot"), Aggregation.project("tot").and("date").previousOperation(), Aggregation.sort(Direction.ASC, "date"));
		
		AggregationResults result = mongoOp.aggregate(agg, Ticket.class, TotAggregate.class);
		
		List<TotAggregate> l = result.getMappedResults();
		return l;
	}

}
