package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.TicketRequest;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketRequestRepository extends MongoRepository<TicketRequest, String>, CustomTicketRequestRepository{

}