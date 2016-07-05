package it.polito.applied.asti.clan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mongodb.MongoException;

import it.polito.applied.asti.clan.exception.BadCredentialsException;
import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.exception.NotFoundException;
import it.polito.applied.asti.clan.pojo.Credential;
import it.polito.applied.asti.clan.pojo.Name;
import it.polito.applied.asti.clan.pojo.Role;
import it.polito.applied.asti.clan.pojo.User;
import it.polito.applied.asti.clan.repository.UserRepository;

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

	/* (non-Javadoc)
	 * @see it.polito.applied.asti.clan.service.UserService#changePassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void changePassword(String username, String oldPassword, String newPassword) throws NotFoundException, BadCredentialsException, BadRequestException {
		User u = null;
		try{
			u = checkOldPassword(username,oldPassword,false);
			if(u==null)
				throw new BadCredentialsException();

			String hashNewPassword=passwordEncoder.encode(newPassword);
			int n = userRepo.changePassword(username, hashNewPassword);
			if(n==0){
				throw new BadRequestException();
			}

		}catch(MongoException e){
			throw e;
		}
	}
	
	private User checkOldPassword(String userEmail, String oldPassword, boolean first) throws NotFoundException{

		User u = userRepo.findByUsername(userEmail);
		if(u==null)
			throw new NotFoundException("User not found");

		
		/*Metodo di BCrypt per comparare una password in chiaro (oldPassword), con la sua versione hashata (u.getPassword()))*/
		if(passwordEncoder.matches(oldPassword, u.getPassword()))
			return u;
		else
			return null;
	}

}
