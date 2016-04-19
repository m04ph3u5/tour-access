package it.polito.applied.asti.clan.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.polito.applied.asti.clan.pojo.InfoEnvironmentSite;
import it.polito.applied.asti.clan.pojo.InfoEnvironmentSonda;
import it.polito.applied.asti.clan.pojo.Poi;
import it.polito.applied.asti.clan.pojo.SensorLog;
import it.polito.applied.asti.clan.pojo.SiteSensorDTO;
import it.polito.applied.asti.clan.pojo.Sonda;
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

	@Override
	public InfoEnvironmentSite getInfoSite(Date start, Date end, String idSite) {
		List<InfoEnvironmentSonda> l = sensorRepo.findInfoSiteInInterval(start, end, idSite);
		if(l!=null){
			InfoEnvironmentSite infoSite = new InfoEnvironmentSite();
			int numRilev = 0;
			infoSite.setNumSonde(l.size());
			float avgTemp = 0, avgHum = 0;
			for (InfoEnvironmentSonda i : l){
				numRilev+=i.getNumRilevazioni();
				Sonda s = new Sonda();
				s.setIdSonda(i.getIdSonda());
				s.setIdSite(idSite);
				infoSite.addSonda(s);
				//aggiungere nome sonda all'oggetto Sonda
				avgTemp += (i.getTemp() * i.getNumRilevazioni());
				avgHum += (i.getHumid() * i.getNumRilevazioni());
			}
			infoSite.setNumRilevazioni(numRilev);
			
			if(numRilev>0){
				infoSite.setTemp(avgTemp/infoSite.getNumRilevazioni());
				infoSite.setHumid(avgHum/infoSite.getNumRilevazioni());
			}
			
			return infoSite;
		}else
			return null;
		
	}


}
