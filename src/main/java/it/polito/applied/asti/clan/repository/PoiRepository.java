package it.polito.applied.asti.clan.repository;

import java.util.List;

import it.polito.applied.asti.clan.pojo.Poi;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PoiRepository extends MongoRepository<Poi, String>, CustomPoiRepository{

	public Poi findByIdPoi(String idPoi);
	public Poi findByIdSite(String idSite);
	public List<Poi> findAll();
	
}
