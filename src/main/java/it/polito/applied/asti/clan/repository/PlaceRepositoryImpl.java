package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.Place;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class PlaceRepositoryImpl implements CustomPlaceRepository{

	@Autowired
	private MongoOperations mongoOp;
	
	@Override
	public boolean isValid(List<String> placesId) {
		int numPlaces = placesId.size();
		Query q = new Query();
		q.addCriteria(Criteria.where("id").in(placesId));
		long tot = mongoOp.count(q, Place.class);
		System.out.println("FOUNDED: "+tot);
		if(tot==numPlaces)
			return true;
		else
			return false;
	}

}
