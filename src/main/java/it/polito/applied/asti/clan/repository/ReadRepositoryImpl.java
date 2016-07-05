package it.polito.applied.asti.clan.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import it.polito.applied.asti.clan.pojo.PoiRank;
import it.polito.applied.asti.clan.pojo.Read;
import it.polito.applied.asti.clan.pojo.TotAggregate;

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
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		long offset = cal.get(Calendar.ZONE_OFFSET) + cal.get(Calendar.DST_OFFSET);
		
		
		Criteria c = new Criteria();
		c = (Criteria.where("dtaTransit").gte(start)
				.andOperator(Criteria.where("dtaTransit").lte(end)
				.andOperator(Criteria.where("isAccepted").is(true))));
		
		Aggregation agg = Aggregation.newAggregation(Aggregation.match(c),
				Aggregation.project()
				.and("dtaTransit").plus(offset).as("dtaTransit"),
				Aggregation.sort(Sort.Direction.DESC, "dtaTransit"),
				Aggregation.project()
				.andExpression("year(dtaTransit)").as("year")
				.andExpression("month(dtaTransit)").as("month")
				.andExpression("dayOfMonth(dtaTransit)").as("day"),
				Aggregation.group(Aggregation.fields().and("year").and("month").and("day"))
				.count().as("tot"));
				
	
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

	/* (non-Javadoc)
	 * @see it.polito.applied.asti.clan.repository.CustomReadRepository#find(java.util.Date, java.util.Date)
	 */
	@Override
	public List<Read> find(Date start, Date end) {
		Query q = new Query();
		q.addCriteria(Criteria.where("dtaTransit").lte(end)
				.andOperator(Criteria.where("dtaTransit").gte(start)));
		q.with(new Sort(Sort.Direction.ASC, "dtaTransit"));
		
		return mongoOp.find(q, Read.class);
	}

}
