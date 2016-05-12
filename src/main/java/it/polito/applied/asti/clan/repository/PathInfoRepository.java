package it.polito.applied.asti.clan.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.asti.clan.pojo.PathInfo;

public interface PathInfoRepository extends MongoRepository<PathInfo, String>, CustomPathInfoRepository{

	public PathInfo findByIdPath(int idPath);

}
