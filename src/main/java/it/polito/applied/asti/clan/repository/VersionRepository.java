package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.Version;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface VersionRepository extends MongoRepository<Version, String>{

}
