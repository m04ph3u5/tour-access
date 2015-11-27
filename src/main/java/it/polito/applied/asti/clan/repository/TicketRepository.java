package it.polito.applied.asti.clan.repository;

import java.util.Date;
import java.util.List;

import it.polito.applied.asti.clan.pojo.Ticket;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TicketRepository extends MongoRepository<Ticket, String>, CustomTicketRepository{
	public Ticket findById(String id);
	public List<Ticket> findByIdTicket(String idTicket);
}
