package it.polito.applied.asti.clan.repository;

import java.util.List;

import it.polito.applied.asti.clan.pojo.Poi;

public interface CustomPoiRepository {
	public boolean isValid(List<String> placesId);
	public List<Poi> findAllPlaceCustom();

}
