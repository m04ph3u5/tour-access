package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.PathInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class PathInfoRepositoryImpl implements CustomPathInfoRepository{

	@Autowired
	private MongoOperations mongoOp;

	@Override
	public void updateAverage(int idPath, float average, int size) {
		Query q = new Query(); 
		q.addCriteria(Criteria.where("idPath").is(idPath));
		Update u = new Update();
		u.set("avgRating", average);
		u.set("numComments", size);
		mongoOp.upsert(q, u, PathInfo.class);
	}



}
