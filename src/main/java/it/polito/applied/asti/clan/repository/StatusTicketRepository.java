package it.polito.applied.asti.clan.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.asti.clan.pojo.StatusTicket;

public interface StatusTicketRepository extends MongoRepository<StatusTicket, String>, CustomStatusTicketRepository{
	public List<StatusTicket> findAll();
}
