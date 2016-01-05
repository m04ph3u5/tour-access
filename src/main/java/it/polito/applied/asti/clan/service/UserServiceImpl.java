package it.polito.applied.asti.clan.service;

import it.polito.applied.asti.clan.exception.NotFoundException;
import it.polito.applied.asti.clan.pojo.Credential;
import it.polito.applied.asti.clan.pojo.Name;
import it.polito.applied.asti.clan.pojo.Role;
import it.polito.applied.asti.clan.pojo.User;
import it.polito.applied.asti.clan.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		System.out.println("LOAD USER BY USERNAME");
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

	@Override
	public boolean validateCredential(Credential credential, boolean isSupervisor) {
		User u = userRepo.findByUsername(credential.getUsername());
		if(u==null)
			return false;
		Role role = u.getRoles().get(0);
		if(role==null)
			return false;
		String r = role.getAuthority();
		if((isSupervisor && r.equals("ROLE_SUPERVISOR")) || (!isSupervisor && r.equals("ROLE_OPERATOR"))){
			System.out.println("IN IF");
			if(passwordEncoder.matches(credential.getPassword(), u.getPassword()))
				return true;
			else
				return false;
		}else
			return false;
	}

}
