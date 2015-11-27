package it.polito.applied.asti.clan.repository;


import it.polito.applied.asti.clan.pojo.RoleTicket;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleTicketRepository extends MongoRepository<RoleTicket, String>, CustomStatusRepository{
	public List<RoleTicket> findAll();
}
