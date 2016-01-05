package it.polito.applied.asti.clan.repository;

import java.util.Date;
import java.util.Map;

public interface CustomLogRepository {
	Map<String, Long> getDateSeries(Date start, Date end);
	Map<String, Long> getDeviceSeries(Date start, Date end);

}
