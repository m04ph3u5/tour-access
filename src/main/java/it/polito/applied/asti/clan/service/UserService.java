package it.polito.applied.asti.clan.service;

import it.polito.applied.asti.clan.exception.NotFoundException;
import it.polito.applied.asti.clan.pojo.Credential;
import it.polito.applied.asti.clan.pojo.Name;

public interface UserService {

	public Name getNameByUserName(String username) throws NotFoundException;

	public boolean validateCredential(Credential credential);

}
