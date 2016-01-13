package it.polito.applied.asti.clan.repository;

import java.util.Date;
import java.util.List;

import it.polito.applied.asti.clan.pojo.Log;
import it.polito.applied.asti.clan.pojo.TotAggregate;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface LogRepository extends MongoRepository<Log, String>, CustomLogRepository{


}
