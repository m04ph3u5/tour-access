package it.polito.applied.asti.clan.security;


import it.polito.applied.asti.clan.exception.ErrorInfo;
import it.polito.applied.asti.clan.exception.JacksonUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component("restServicesEntryPoint")
public class AuthenticationEntryPointRest extends BasicAuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException
	{
		String path = request.getRequestURI().substring(request.getContextPath().length());
		System.out.println("COMMENCE: "+path);
		if (path.startsWith("/assets") || path.startsWith("/api")) {
			ErrorInfo e = new ErrorInfo();
			e.setStatusCode(HttpStatus.UNAUTHORIZED.toString());
			e.setMessage("Devi essere loggato per accedere a questa risorsa");
			response.addHeader("WWW-Authenticate", "xBasic realm=\"" + getRealmName() + "\"");
		    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			String json = JacksonUtil.toJSON(e);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.getWriter().println(json);
		} else{
		    response.sendRedirect("/#"+path);
		}
	}
	
	@Override
    public void afterPropertiesSet() throws Exception {
		setRealmName("AstiMusei");
        super.afterPropertiesSet();
    }
}
