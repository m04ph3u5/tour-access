package it.polito.applied.asti.clan.service;

import it.polito.applied.asti.clan.pojo.VersionZip;

public interface AppService {

	public VersionZip getVersion();

	public boolean checkTicket(String ticket);
}
