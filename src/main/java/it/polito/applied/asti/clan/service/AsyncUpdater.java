package it.polito.applied.asti.clan.service;

import it.polito.applied.asti.clan.pojo.UserMessage;

public interface AsyncUpdater {
	
	public void sendEmailFromSite(UserMessage userMessage);

}
