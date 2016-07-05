/**
 * 
 */
package it.polito.applied.asti.clan.service;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import it.polito.applied.asti.clan.exception.ServiceUnaivalableException;

/**
 * @author m04ph3u5
 *
 */
public interface GenerateReport {

	public void ticketsReport(Date start, Date end, HttpServletResponse response) throws ServiceUnaivalableException, IOException;
	
	public void entrancesReport(Date start, Date end, HttpServletResponse response) throws ServiceUnaivalableException, IOException;

	public void environmentReport(Date start, Date end, HttpServletResponse response) throws ServiceUnaivalableException, IOException;
}
