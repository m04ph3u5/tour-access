package it.polito.applied.asti.clan.repository;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.WriteResult;

import it.polito.applied.asti.clan.pojo.InfoTicketRequest;
import it.polito.applied.asti.clan.pojo.StatisticsInfo;
import it.polito.applied.asti.clan.pojo.TicketRequest;

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
	public StatisticsInfo getStatisticsInfoGroup(Date start, Date end) {
		Criteria c = new Criteria();
		c = Criteria.where("requestDate").gte(start)
				.andOperator(Criteria.where("requestDate").lte(end)
				.andOperator(Criteria.where("acceptedFromAcl").is(true)
				.andOperator(Criteria.where("isGroup").is(true))));
		
		Aggregation agg;
		agg = Aggregation.newAggregation(Aggregation.match(c),
				Aggregation.group("isGroup")
				);
		
		AggregationResults result = mongoOp.aggregate(agg, TicketRequest.class, StatisticsInfo.class);
		StatisticsInfo s = (StatisticsInfo)result.getUniqueMappedResult();
		return s;
	}

	@Override
	public StatisticsInfo getStatisticsInfoSingle(Date start, Date end) {
		Criteria c = new Criteria();
		c = Criteria.where("requestDate").gte(start)
				.andOperator(Criteria.where("requestDate").lte(end)
				.andOperator(Criteria.where("acceptedFromAcl").is(true)
				.andOperator(Criteria.where("isGroup").is(false))));
		
		Aggregation agg;
		return null;
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
				.andOperator(Criteria.where("info.age").is("middleAge")
				.andOperator(Criteria.where("acceptedFromAcl").is(true)))));
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

	@Override
	public void removeTicketInTicketRequest(String ticketRequestId, String ticketId) {
		Query q = new Query();
		q.addCriteria(Criteria.where("id").is(ticketRequestId));	
		TicketRequest request = mongoOp.findOne(q, TicketRequest.class);
		if(request != null){
			if(request.isGroup() && request.getTicketNumbers().size()>=3){ //ticketRequest di gruppo -> non posso cancellarla perchè ad essa fanno riferimento almeno altri due ticket oltre quello che sto cancellando
				
				Update u = new Update();
				u.pull("ticketNumbers", ticketId);
				u.set("someTicketIsCanceled", true);
				WriteResult w = mongoOp.updateFirst(q, u, TicketRequest.class);
				if(w.getN()==0){
					Update u2= new Update();
					u2.set("someTicketIsCanceled", false);
					mongoOp.updateFirst(q, u2, TicketRequest.class);
				}
		
			}else if(request.isGroup() && request.getTicketNumbers().size()==2){ //ticketRequest di gruppo che deve diventare singola perchè c'è un solo altro ticket nella ticketRequest
				
				InfoTicketRequest info = new InfoTicketRequest();
				info.setAge("male");
				info.setGender("middleAge");
				Update u = new Update();
				u.pull("ticketNumbers", ticketId);
				u.set("isGroup", false);
				u.set("info", info);
				
				mongoOp.updateFirst(q, u, TicketRequest.class);
			}else{   //la ticketRequest è legata al solo biglietto che sto cancellando, quindi posso cancellarla direttamente

				mongoOp.remove(request);
			}
		}
		
		
		
	}

	
	

}
