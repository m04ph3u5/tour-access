package it.polito.applied.asti.clan.service;

import it.polito.applied.asti.clan.exception.BadCredentialsException;
import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.exception.NotFoundException;
import it.polito.applied.asti.clan.pojo.Credential;
import it.polito.applied.asti.clan.pojo.Name;

public interface UserService {

	public Name getNameByUserName(String username) throws NotFoundException;

	public boolean validateCredential(Credential credential, boolean isSupervisor);

	/**
	 * @param userEmail
	 * @param oldPassword
	 * @param newPassword
	 * @throws NotFoundException 
	 * @throws BadCredentialsException 
	 * @throws BadRequestException 
	 */
	public void changePassword(String userEmail, String oldPassword, String newPassword) throws NotFoundException, BadCredentialsException, BadRequestException;

}
