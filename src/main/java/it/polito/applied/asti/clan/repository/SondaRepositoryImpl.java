package it.polito.applied.asti.clan.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

public class SondaRepositoryImpl implements CustomSondaRepository{

	@Autowired
	private MongoOperations mongoOp;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> findDistinctSondaSite() {
		return mongoOp.getCollection("sonda").distinct("idSite");
	}

}
