package it.polito.applied.asti.clan.service;

import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.pojo.CommentsPage;
import it.polito.applied.asti.clan.pojo.CommentsRequest;
import it.polito.applied.asti.clan.pojo.LogDTO;
import it.polito.applied.asti.clan.pojo.LogSeriesInfo;
import it.polito.applied.asti.clan.pojo.VersionZip;

import java.util.Date;
import java.util.List;

public interface AppService {

	public VersionZip getVersion();

	public boolean checkTicket(String ticket);

	public CommentsPage getComments(CommentsRequest request) throws BadRequestException;

	public void postComment(List<LogDTO> logComment) throws BadRequestException;

	public void postLog(List<LogDTO> logs);

	public LogSeriesInfo getLogInfo(Date start, Date end);

	public long getAccess(Date date);

	public long getInstallation(Date date);

	public long getDevices(Date date);
}
