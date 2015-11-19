package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.Ticket;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketRepository extends MongoRepository<Ticket, String>, CustomTicketRepository{
	public Ticket findById();
	public Ticket findByTicketNumber();
}
