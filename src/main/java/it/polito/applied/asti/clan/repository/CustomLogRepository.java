package it.polito.applied.asti.clan.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import it.polito.applied.asti.clan.pojo.TotAggregate;

public interface CustomLogRepository {
	Map<String, Long> getDateSeries(Date start, Date end);
	Map<String, Long> getDeviceSeries(Date start, Date end);
	long countNumberAccessFromDate(Date date);
	long countNumberInstallationFromDate(Date date);
	long distinctDevicesFromDate(Date date);
	boolean isDeviceInstalled(String deviceId);
	List<TotAggregate> getAccessGrouped(Date start, Date end);
	List<TotAggregate> getInstallGrouped(Date start, Date end);
	List<TotAggregate> getPathStarted(Date start, Date end);
	List<TotAggregate> getCheckedTicket(Date start, Date end);
}
