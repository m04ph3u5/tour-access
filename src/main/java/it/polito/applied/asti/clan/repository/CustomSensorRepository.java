package it.polito.applied.asti.clan.repository;

import java.util.Date;
import java.util.List;

import it.polito.applied.asti.clan.pojo.InfoEnvironmentSonda;
import it.polito.applied.asti.clan.pojo.SensorLog;
import it.polito.applied.asti.clan.pojo.SiteSensorDTO;
import it.polito.applied.asti.clan.pojo.TotAvgAggregate;

public interface CustomSensorRepository {

	public List<String> getSites();
	public List<SiteSensorDTO> getAvgs(Date start);
	public List<InfoEnvironmentSonda> findInfoSiteInInterval(Date start, Date end, String idSite);
	public List<TotAvgAggregate> getAvgSeriesTemperatureAndHumidity(String idSite, Date startDate, Date endDate, boolean hourGranualarity);
	/**
	 * @param start
	 * @param end
	 * @return
	 */
	public List<SensorLog> find(Date start, Date end);
}
