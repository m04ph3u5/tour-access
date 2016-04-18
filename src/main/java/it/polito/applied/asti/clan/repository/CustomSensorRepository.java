package it.polito.applied.asti.clan.repository;

import java.util.Date;
import java.util.List;

import it.polito.applied.asti.clan.pojo.SiteSensorDTO;

public interface CustomSensorRepository {

	public List<String> getSites();
	public List<SiteSensorDTO> getAvgs(Date start);
}
