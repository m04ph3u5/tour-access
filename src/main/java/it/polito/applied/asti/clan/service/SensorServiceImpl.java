package it.polito.applied.asti.clan.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.polito.applied.asti.clan.pojo.Poi;
import it.polito.applied.asti.clan.pojo.SensorLog;
import it.polito.applied.asti.clan.pojo.SiteSensorDTO;
import it.polito.applied.asti.clan.repository.PoiRepository;
import it.polito.applied.asti.clan.repository.SensorRepository;

@Service
public class SensorServiceImpl implements SensorService{
	
	@Autowired
	private SensorRepository sensorRepo;
	
	@Autowired
	private PoiRepository poiRepo;

	@Override
	public void saveSensorLog(SensorLog log) {
		sensorRepo.save(log);
	}

	@Override
	public List<SiteSensorDTO> getMonitoredSiteInfo(Date date) {
		
		List<SiteSensorDTO> list = sensorRepo.getAvgs(date);
		if(list!=null){
			for(SiteSensorDTO ss : list){
				Poi p = poiRepo.findByIdSite(ss.getIdSite());
				ss.setName(p.getName());
			}
		}
		
		return list;
	}


}
