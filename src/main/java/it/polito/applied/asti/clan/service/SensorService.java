package it.polito.applied.asti.clan.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import it.polito.applied.asti.clan.pojo.InfoEnvironmentSite;
import it.polito.applied.asti.clan.pojo.SensorLog;
import it.polito.applied.asti.clan.pojo.SiteSensorDTO;
import it.polito.applied.asti.clan.pojo.TotAvgAggregate;

public interface SensorService {

	public void saveSensorLog(SensorLog log);

	public List<SiteSensorDTO> getMonitoredSiteInfo(Date date);
	public InfoEnvironmentSite getInfoSite(Date start, Date end, String idSite);

	public Map<String, Map<Date, TotAvgAggregate>> getEnvironmentSeries(String idSite, Date startDate, Date endDate);

}
