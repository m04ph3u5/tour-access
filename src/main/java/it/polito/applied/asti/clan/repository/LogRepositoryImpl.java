package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class LogRepositoryImpl implements CustomLogRepository{

	@Autowired
	private MongoOperations mongoOp;
	
	@Override
	public Map<String, Long> getDateSeries(Date start, Date end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Long> getDeviceSeries(Date start, Date end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long countNumberAccessFromDate(Date date) {
		Query q = new Query();
		q.addCriteria(Criteria.where("date").gte(date));
		return mongoOp.count(q, Log.class);
	}

	@Override
	public long countNumberInstallationFromDate(Date date) {
		DBObject q = new BasicDBObject("date", new BasicDBObject("$lt",date));
		List<?> l = mongoOp.getCollection("Log").distinct("deviceId", q);
		
		if(l!=null){
			BasicDBObject andQuery = new BasicDBObject();
			List<BasicDBObject> obj = new ArrayList<BasicDBObject>();
			obj.add(new BasicDBObject("deviceId", new BasicDBObject("$nin",l)));
			obj.add(new BasicDBObject("date", new BasicDBObject("$gte",date)));
			andQuery.put("$and", obj);
			List<?> l2 = mongoOp.getCollection("log").distinct("deviceId", andQuery);
			if(l2!=null)
				return l2.size();
			else
				return 0;
		}else
			return 0;
	}

	@Override
	public long distinctDevicesFromDate(Date date) {
	    DBObject q = new BasicDBObject("date", new BasicDBObject("$gte",date));
		List<?> l = mongoOp.getCollection("log").distinct("deviceId", q);
		if(l!=null)
			return l.size();
		else
			return 0;

	}

}
