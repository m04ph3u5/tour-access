package it.polito.applied.asti.clan.service;

import it.polito.applied.asti.clan.exception.NotFoundException;
import it.polito.applied.asti.clan.pojo.Name;
import it.polito.applied.asti.clan.pojo.User;
import it.polito.applied.asti.clan.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {

		User u = userRepo.findByUsername(username);
		return u;
	}

	@Override
	public Name getNameByUserName(String username) throws NotFoundException {
		User u = userRepo.findByUsername(username);
		if(u==null)
			throw new NotFoundException();
		return new Name(u.getFirstname(),u.getLastname());
	}

}
