package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.Poi;

import java.util.List;

public interface CustomPoiRepository {
	public boolean isValid(List<String> placesId);
	public List<Poi> findAllPlaceCustom();

}
