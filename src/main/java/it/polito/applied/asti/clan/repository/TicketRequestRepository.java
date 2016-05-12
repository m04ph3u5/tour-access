package it.polito.applied.asti.clan.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.asti.clan.pojo.TicketRequest;

public interface TicketRequestRepository extends MongoRepository<TicketRequest, String>, CustomTicketRequestRepository{



}
