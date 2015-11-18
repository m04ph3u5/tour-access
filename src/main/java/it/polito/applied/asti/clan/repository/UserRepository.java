package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.User;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>, CustomUserRepository{
	
	public User findById(String id);
	public User findByUsername(String username);

}
