package it.polito.applied.asti.clan.repository;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

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

}
