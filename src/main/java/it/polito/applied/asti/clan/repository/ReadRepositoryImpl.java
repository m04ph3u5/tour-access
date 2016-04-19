package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.PoiRank;
import it.polito.applied.asti.clan.pojo.Read;
import it.polito.applied.asti.clan.pojo.TotAggregate;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class ReadRepositoryImpl implements CustomReadRepository{

	@Autowired
	private MongoOperations mongoOp;
	
	@Override
	public long countIngressFromDate(Date date) {
		Query q = new Query();
		q.addCriteria(Criteria.where("isAccepted").is(true)
				.andOperator(Criteria.where("dtaTransit").gte(date)));
		
		return mongoOp.count(q, Read.class);
	}

	@Override
	public long countIngress(Date start, Date end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("isAccepted").is(true)
				.andOperator(Criteria.where("dtaTransit").gte(start)
						.andOperator(Criteria.where("dtaTransit").lte(end))));
		
		return mongoOp.count(q, Read.class);
	}
	
	@Override
	public List<TotAggregate> getAccessGrouped(Date start, Date end){
		Criteria c = new Criteria();
		c = (Criteria.where("dtaTransit").gte(start)
				.andOperator(Criteria.where("dtaTransit").lte(end)
				.andOperator(Criteria.where("isAccepted").is(true))));
		
		
		
		Aggregation agg = Aggregation.newAggregation(Aggregation.match(c), 
				Aggregation.group("dtaTransit").count().as("tot"), 
				Aggregation.project("tot").and("date").previousOperation(), 
				Aggregation.sort(Direction.ASC, "date"));
		
		AggregationResults result = mongoOp.aggregate(agg, Read.class, TotAggregate.class);
		
		List<TotAggregate> l = result.getMappedResults();
		
		return l;
	}

	@Override
	public List<PoiRank> getPoiRank(Date start, Date end) {
		Criteria c = new Criteria();
		c = (Criteria.where("dtaTransit").gte(start)
				.andOperator(Criteria.where("dtaTransit").lte(end)
				.andOperator(Criteria.where("isAccepted").is(true))));
		
		
		
		Aggregation agg = Aggregation.newAggregation(Aggregation.match(c), Aggregation.group("idSite").count().as("totAccess"), Aggregation.project("totAccess").and("idSite").previousOperation(), Aggregation.sort(Direction.DESC, "totAccess"));
		
		AggregationResults result = mongoOp.aggregate(agg, Read.class, PoiRank.class);
		
		List<PoiRank> l = result.getMappedResults();
		return l;
	}

}
