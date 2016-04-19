package it.polito.applied.asti.clan.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.asti.clan.pojo.Sonda;

public interface SondaRepository extends MongoRepository<Sonda, String>, CustomSondaRepository{

	public Sonda findById(String id);
	public Sonda findByIdSondaAndIdSite(String idSonda, String idSite);
	public List<Sonda> findByIdSite(String idSite);
	
}
