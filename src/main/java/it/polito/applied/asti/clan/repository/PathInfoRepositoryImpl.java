package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.PathInfo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class PathInfoRepositoryImpl implements CustomPathInfoRepository{

	@Autowired
	private MongoOperations mongoOp;

	@Override
	public void updatePathsInfo(List<PathInfo> paths) {
		
	}
	


}
