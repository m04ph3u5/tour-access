package it.polito.applied.asti.clan.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import it.polito.applied.asti.clan.pojo.Poi;

public class PoiRepositoryImpl implements CustomPoiRepository{

	@Autowired
	private MongoOperations mongoOp;
	
	@Override
	public boolean isValid(List<String> placesId) {
		int numPlaces = placesId.size();
		Query q = new Query();
		q.addCriteria(Criteria.where("idPoi").in(placesId));
		long tot = mongoOp.count(q, Poi.class);
		System.out.println("FOUNDED: "+tot);
		if(tot==numPlaces)
			return true;
		else
			return false;
	}

	@Override
	public List<Poi> findAllPlaceCustom() {
		Query q = new Query();
		
		q.fields().exclude("idPoi");
		q.fields().exclude("imageId");
		q.fields().exclude("markerImg");
		q.fields().exclude("rating");
		q.fields().exclude("title");
		q.fields().exclude("longDescription");
		q.fields().exclude("gallery");
		q.fields().exclude("hotspotList");
		q.fields().exclude("audio");
		q.fields().exclude("pano");
		
		return mongoOp.find(q, Poi.class);
	}

}
