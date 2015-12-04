package it.polito.applied.asti.clan.service;

import java.util.List;

import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.exception.NotFoundException;
import it.polito.applied.asti.clan.pojo.CommentsPage;
import it.polito.applied.asti.clan.pojo.CommentsRequest;
import it.polito.applied.asti.clan.pojo.LogDTO;
import it.polito.applied.asti.clan.pojo.VersionZip;

public interface AppService {

	public VersionZip getVersion();

	public boolean checkTicket(String ticket);

	public CommentsPage getComments(CommentsRequest request) throws NotFoundException, BadRequestException;

	public void postComment(List<LogDTO> logComment) throws BadRequestException;

	public void postLog(List<LogDTO> logs);
}
