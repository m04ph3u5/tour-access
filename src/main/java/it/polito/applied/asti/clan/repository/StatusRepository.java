package it.polito.applied.asti.clan.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sun.mail.imap.protocol.Status;

public interface StatusRepository extends MongoRepository<Status, String>, CustomStatusRepository{
	public List<Status> findAll();
}
