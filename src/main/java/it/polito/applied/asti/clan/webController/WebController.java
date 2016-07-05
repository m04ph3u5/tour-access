package it.polito.applied.asti.clan.webController;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import it.polito.applied.asti.clan.exception.ServiceUnaivalableException;
import it.polito.applied.asti.clan.service.GenerateReport;

@Controller
public class WebController {
	
	@Autowired
	private GenerateReport generateReport;
	
	@RequestMapping(value="/contatti",method=RequestMethod.GET)
	public String contactUs() {
		System.out.println("contatti");
	    return "/template/contact-us.html";
	}
	
	@RequestMapping(value="/progetto",method=RequestMethod.GET)
	public String project() {
		System.out.println("progetto");
	    return "/template/project.html";
	}
	
	@RequestMapping(value="/app",method=RequestMethod.GET)
	public String app() {
		System.out.println("app");
	    return "/template/app-page.html";
	}
	
	@RequestMapping(value="/termini-del-servizio",method=RequestMethod.GET)
	public String terms() {
		System.out.println("terms");
	    return "/template/terms.html";
	}
	
	@RequestMapping(value="/termini-del-servizio-app",method=RequestMethod.GET)
	public String termsApp() {
		System.out.println("terms");
	    return "/template/termsApp.html";
	}
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String indexExplicit() {
		System.out.println("index");
	    return "/template/index.html";
	}
	
	@RequestMapping(value="/operator",method=RequestMethod.GET)
	public String operator() {
		System.out.println("operator");
	    return "/template/indexOperator.html";
	}
	
	@RequestMapping(value="/operator/**",method=RequestMethod.GET)
	public String operatorApp() {
		System.out.println("operator");
	    return "/template/indexOperator.html";
	}
	
	@RequestMapping(value="/supervisor",method=RequestMethod.GET)
	public String supervisor() {
		System.out.println("supervisor");
	    return "/template/indexSupervisor.html";
	}
	
	@RequestMapping(value="/supervisor/**",method=RequestMethod.GET)
	public String supervisorApp() {
		System.out.println("supervisor");
	    return "/template/indexSupervisor.html";
	}
	
	@PreAuthorize("hasRole('ROLE_SUPERVISOR')")
	@RequestMapping(value="/report/tickets", method=RequestMethod.GET)
	public HttpServletResponse downloadTicketReport(@RequestParam(value="start", required=true) Long start, 
			@RequestParam(value="end", required=true) Long end, HttpServletResponse response) throws ServiceUnaivalableException, IOException {
		Date startDate=null, endDate=null;
		
		
		if(start==null || start<=0 || end==null || end<=0 || end<start){
			startDate = new Date();
			endDate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			startDate = cal.getTime();
			cal.setTime(endDate);
			cal.add(Calendar.DAY_OF_MONTH, -7);
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
			endDate = cal.getTime();
		}else{
			startDate = new Date(start);
			endDate = new Date(end);
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			startDate = cal.getTime();
			cal.setTime(endDate);
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
		}
		generateReport.ticketsReport(startDate, endDate, response);
		return response;
	}
	
	@PreAuthorize("hasRole('ROLE_SUPERVISOR')")
	@RequestMapping(value="/report/entrances", method=RequestMethod.GET)
	public HttpServletResponse downloadEntrnaceReport(@RequestParam(value="start", required=true) Long start, 
			@RequestParam(value="end", required=true) Long end, HttpServletResponse response) throws ServiceUnaivalableException, IOException {
		Date startDate=null, endDate=null;
		
		
		if(start==null || start<=0 || end==null || end<=0 || end<start){
			startDate = new Date();
			endDate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			startDate = cal.getTime();
			cal.setTime(endDate);
			cal.add(Calendar.DAY_OF_MONTH, -7);
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
			endDate = cal.getTime();
		}else{
			startDate = new Date(start);
			endDate = new Date(end);
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			startDate = cal.getTime();
			cal.setTime(endDate);
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
		}
		generateReport.entrancesReport(startDate, endDate, response);
		return response;
	}
	
	@PreAuthorize("hasRole('ROLE_SUPERVISOR')")
	@RequestMapping(value="/report/environment", method=RequestMethod.GET)
	public HttpServletResponse downloadEnvironmentReport(@RequestParam(value="start", required=true) Long start, 
			@RequestParam(value="end", required=true) Long end, HttpServletResponse response) throws ServiceUnaivalableException, IOException {
		Date startDate=null, endDate=null;
		
		
		if(start==null || start<=0 || end==null || end<=0 || end<start){
			startDate = new Date();
			endDate = new Date();
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			startDate = cal.getTime();
			cal.setTime(endDate);
			cal.add(Calendar.DAY_OF_MONTH, -7);
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
			endDate = cal.getTime();
		}else{
			startDate = new Date(start);
			endDate = new Date(end);
			Calendar cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			startDate = cal.getTime();
			cal.setTime(endDate);
			cal.set(Calendar.HOUR, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
		}
		generateReport.environmentReport(startDate, endDate, response);
		return response;
	}
	
	@RequestMapping(value="/404",method=RequestMethod.GET)
	public String redirectTo404() {
		System.out.println("404");
	    return "/template/404.html";
	}
	
	@RequestMapping(value="/503",method=RequestMethod.GET)
	public String redirectTo503() {
		System.out.println("503");
	    return "/template/503.html";
	}
	
	@RequestMapping(value="/",method=RequestMethod.GET)
	public String redirectToPlayStore(@RequestParam(value="c", required=false) String code) {
		if(code!=null && !code.isEmpty())
			return "redirect:https://play.google.com/store/apps/details?id=it.tonicminds.dev.astimusei";
		else
		    return "index";
	}
	
	@RequestMapping(value="/**",method=RequestMethod.GET)
	public String index() {
		System.out.println("index");
	    return "/template/index.html";
	}
}
