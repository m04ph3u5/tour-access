package it.polito.applied.asti.clan.repository;

import java.util.Date;
import java.util.List;

import it.polito.applied.asti.clan.pojo.PoiRank;
import it.polito.applied.asti.clan.pojo.Read;
import it.polito.applied.asti.clan.pojo.TotAggregate;

public interface CustomReadRepository {
	public long countIngressFromDate(Date date);
	public long countIngress(Date start, Date end);
	List<TotAggregate> getAccessGrouped(Date start, Date end);
	public List<PoiRank> getPoiRank(Date start, Date end);	
	/**
	 * @param start
	 * @param end
	 * @return
	 */
	public List<Read> find(Date start, Date end);
	public List<Read> findAcceptedAfter(Date from);
	
	public long countIngressFromDateV2(Date date);
	public long countIngressV2(Date start, Date end);
	List<TotAggregate> getAccessGroupedV2(Date start, Date end);

}
