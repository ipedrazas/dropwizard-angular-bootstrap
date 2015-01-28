package me.pedrazas.dropangular;

import io.dropwizard.hibernate.UnitOfWork;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import me.pedrazas.dropangular.om.Ping;

import com.codahale.metrics.annotation.Timed;


@Path("/ping")
@Produces(MediaType.APPLICATION_JSON)
public class PingResource {
	
	private final PingDAO dao;

	public PingResource(PingDAO pingDAO) {
		super();
		this.dao = pingDAO;
	}

	@GET
	@UnitOfWork
	public List<Ping> listCheckPoints() {
		return dao.findAll();
	}
	
	@POST
	@Timed
	@UnitOfWork
	public Response startUp(@Context HttpServletRequest request) {
		Ping ping = new Ping(getClientIpAddr(request));
		try {			
			dao.create(ping);
			return Response.status(Response.Status.ACCEPTED).entity(ping).build();
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity(ping).build();
		}
	}
	
	
	private static String getClientIpAddr(HttpServletRequest request) {  
        String ip = request.getHeader("X-Forwarded-For");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }
}
