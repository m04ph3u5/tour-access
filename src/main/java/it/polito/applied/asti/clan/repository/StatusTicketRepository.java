package it.polito.applied.asti.clan.repository;


import it.polito.applied.asti.clan.pojo.StatusTicket;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StatusTicketRepository extends MongoRepository<StatusTicket, String>, CustomStatusTicketRepository{
	public List<StatusTicket> findAll();
}
