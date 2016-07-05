package it.polito.applied.asti.clan.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.applied.asti.clan.pojo.Ticket;

public interface TicketRepository extends MongoRepository<Ticket, String>, CustomTicketRepository{
	public Ticket findById(String id);
	public List<Ticket> findByIdTicket(String idTicket);
}
