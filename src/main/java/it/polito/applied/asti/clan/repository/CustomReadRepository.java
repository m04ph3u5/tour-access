package it.polito.applied.asti.clan.repository;

import it.polito.applied.asti.clan.pojo.PoiRank;
import it.polito.applied.asti.clan.pojo.TotAggregate;

import java.util.Date;
import java.util.List;

public interface CustomReadRepository {
	public long countIngressFromDate(Date date);
	public long countIngress(Date start, Date end);
	List<TotAggregate> getAccessGrouped(Date start, Date end);
	public List<PoiRank> getPoiRank(Date start, Date end);	

}
