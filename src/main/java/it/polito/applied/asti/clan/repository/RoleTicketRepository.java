package it.polito.applied.asti.clan.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.asti.clan.pojo.RoleTicket;

public interface RoleTicketRepository extends MongoRepository<RoleTicket, String>, CustomStatusTicketRepository{
	public List<RoleTicket> findAll();
}
