package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.PathInfo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PathInfoRepository extends MongoRepository<PathInfo, String>, CustomPathInfoRepository{

	public PathInfo findByIdPath(int idPath);

}
