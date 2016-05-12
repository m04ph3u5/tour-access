package it.polito.applied.asti.clan.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import it.polito.applied.asti.clan.exception.BadRequestException;
import it.polito.applied.asti.clan.exception.ConflictException;
import it.polito.applied.asti.clan.pojo.AppAccessInstallSeries;
import it.polito.applied.asti.clan.pojo.AppInfo;
import it.polito.applied.asti.clan.pojo.CheckTicketInput;
import it.polito.applied.asti.clan.pojo.CommentsPage;
import it.polito.applied.asti.clan.pojo.CommentsRequest;
import it.polito.applied.asti.clan.pojo.LogDTO;
import it.polito.applied.asti.clan.pojo.LogSeriesInfo;

public interface AppService {

	public int getVersion();

	public boolean checkTicket(CheckTicketInput t);

	public CommentsPage getComments(CommentsRequest request) throws BadRequestException;

	public void postComment(List<LogDTO> logComment, int appVersion) throws BadRequestException;

	public void postLog(List<LogDTO> logs, int appVersion);

	public LogSeriesInfo getLogInfo(Date start, Date end);

	public long getAccess(Date date);

	public long getInstallation(Date date);

	public long getDevices(Date date);

	public Map<Date, AppAccessInstallSeries> getAppInstallAccessSeries(
			Date start, Date end);

	public Map<Date, AppInfo> getAppInfo(Date start, Date end);

	public void checkAppVersion(int appVersion) throws ConflictException;
}
