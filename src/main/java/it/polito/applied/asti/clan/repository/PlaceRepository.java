package it.polito.applied.asti.clan.repository;

import java.util.List;

import it.polito.applied.asti.clan.pojo.Place;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PlaceRepository extends MongoRepository<Place, String>, CustomPlaceRepository{

	public Place findById(String id);
	public List<Place> findAll();
}
