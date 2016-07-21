package it.polito.applied.asti.clan.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.polito.applied.asti.clan.pojo.InfoEnvironmentSite;
import it.polito.applied.asti.clan.pojo.InfoEnvironmentSonda;
import it.polito.applied.asti.clan.pojo.Poi;
import it.polito.applied.asti.clan.pojo.SensorLog;
import it.polito.applied.asti.clan.pojo.SiteSensorDTO;
import it.polito.applied.asti.clan.pojo.TotAvgAggregate;
import it.polito.applied.asti.clan.repository.PoiRepository;
import it.polito.applied.asti.clan.repository.SensorRepository;
import it.polito.applied.asti.clan.repository.SondaRepository;

@Service
public class SensorServiceImpl implements SensorService{
	
	@Autowired
	private SensorRepository sensorRepo;
	
	@Autowired
	private PoiRepository poiRepo;
	
	@Autowired
	private SondaRepository sondaRepo;

	@Override
	public void saveSensorLog(SensorLog log) {
		sensorRepo.save(log);
	}

	@Override
	public List<SiteSensorDTO> getMonitoredSiteInfo(Date date) {
		List<SiteSensorDTO> list = sensorRepo.getAvgs(date);
		if(list!=null && !list.isEmpty()){
			for(SiteSensorDTO ss : list){
				Poi p = poiRepo.findByIdSite(ss.getIdSite());
				ss.setName(p.getName());
			}
			return list;
		}else{
			List<String> sitesHaveSonde = sondaRepo.findDistinctSondaSite();
			if(sitesHaveSonde!=null){
				List<SiteSensorDTO> l = new ArrayList<SiteSensorDTO>();
				for(String s : sitesHaveSonde){
					Poi p = poiRepo.findByIdSite(s);
					SiteSensorDTO siteSensor = new SiteSensorDTO();
					siteSensor.setIdSite(s);
					siteSensor.setName(p.getName());
					l.add(siteSensor);
				}
				return l;
			}else
				return null;
		}
	}

	@Override
	public InfoEnvironmentSite getInfoSite(Date start, Date end, String idSite) {
//		sensorRepo.getAvgSeriesTemperatureAndHumidity(idSite, 2, start, end, true);
		List<InfoEnvironmentSonda> l = sensorRepo.findInfoSiteInInterval(start, end, idSite);
		if(l!=null){
			InfoEnvironmentSite infoSite = new InfoEnvironmentSite();
			int numRilev = 0;
			infoSite.setNumSonde(l.size());
			float avgTemp = 0, avgHum = 0;
			for (InfoEnvironmentSonda i : l){
				numRilev+=i.getNumRilevazioni();
//				Sonda s = new Sonda();
//				s.setIdSonda(i.getIdSonda());
//				s.setIdSite(idSite);
//				infoSite.addSonda(s);
				avgTemp += (i.getTemp() * i.getNumRilevazioni());
				avgHum += (i.getHumid() * i.getNumRilevazioni());
			}
			infoSite.setNumRilevazioni(numRilev);
			infoSite.setSonde(sondaRepo.findByIdSite(idSite));
			if(numRilev>0){
				infoSite.setTemp(avgTemp/infoSite.getNumRilevazioni());
				infoSite.setHumid(avgHum/infoSite.getNumRilevazioni());
			}
			
			return infoSite;
		}else
			return null;
		
	}

	@Override
	public Map<Date, List<TotAvgAggregate>> getEnvironmentSeries(String idSite, Date startDate, Date endDate) {
		Map<Date, List<TotAvgAggregate>> map=null;
		map = new TreeMap<Date, List<TotAvgAggregate>>();
		
		Calendar cal = Calendar.getInstance();
		boolean hourGranularity = getGranularity(startDate, endDate);
		List<TotAvgAggregate> aggregates = sensorRepo.getAvgSeriesTemperatureAndHumidity(idSite, startDate, endDate, hourGranularity);
		
		for(TotAvgAggregate tt : aggregates){
			if(tt.getTot()==0)
				continue;
			cal.set(Calendar.YEAR, tt.getYear());
			cal.set(Calendar.MONTH, tt.getMonth()-1);
			cal.set(Calendar.DAY_OF_MONTH, tt.getDay());
			if(hourGranularity)
				cal.set(Calendar.HOUR_OF_DAY, tt.getHour());
			else
				cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);

			Date d = cal.getTime();
			if(map.containsKey(d)){
				map.get(d).add(tt);
			}else{
				List<TotAvgAggregate> l = new ArrayList<TotAvgAggregate>();
				l.add(tt);
				map.put(d, l);
			}
		}
		return map;
	}

	private boolean getGranularity(Date startDate, Date endDate) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(startDate);
		cal2.setTime(endDate);
		boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
		                  cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
		
		return sameDay;
	}

}
