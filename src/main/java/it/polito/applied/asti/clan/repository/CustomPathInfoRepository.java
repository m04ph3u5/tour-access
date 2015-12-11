package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.PathInfo;

import java.util.List;

public interface CustomPathInfoRepository {

	public void updateAverage(int idPath, float average, int size); 

}
