package it.polito.applied.asti.clan.service;

import it.polito.applied.asti.clan.pojo.VersionZip;
import it.polito.applied.asti.clan.repository.VersionZipRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AppServiceImpl implements AppService{
	
	@Autowired
	private VersionZipRepository versionRepo;

	@Override
	public VersionZip getVersion() {
		Sort s = new Sort(new Sort.Order(Sort.Direction.DESC, "id"));
		return versionRepo.findAll(s).get(0);
	}

}
