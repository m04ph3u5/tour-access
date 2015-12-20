package it.polito.applied.asti.clan.webController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {

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
	
	@RequestMapping(value="/index",method=RequestMethod.GET)
	public String indexExplicit() {
		System.out.println("index");
	    return "/template/index.html";
	}
	
//	@RequestMapping(value="/operator",method=RequestMethod.GET)
//	public String operator() {
//		System.out.println("operator");
//	    return "/template/indexOperator.html";
//	}
//	
//	@RequestMapping(value="/supervisor",method=RequestMethod.GET)
//	public String supervisor() {
//		System.out.println("supervisor");
//	    return "/template/supervisor.html";
//	}
	
	@RequestMapping(value="/404",method=RequestMethod.GET)
	public String redirectTo404() {
		System.out.println("404");
	    return "/template/404.html";
	}
	
	@RequestMapping(value="/**",method=RequestMethod.GET)
	public String index() {
		System.out.println("index");
	    return "/template/index.html";
	}
}
