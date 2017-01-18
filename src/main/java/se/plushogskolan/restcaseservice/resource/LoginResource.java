package se.plushogskolan.restcaseservice.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import se.plushogskolan.restcaseservice.exception.UnathorizedException;
import se.plushogskolan.restcaseservice.model.AccessBean;
import se.plushogskolan.restcaseservice.model.LoginBean;
import se.plushogskolan.restcaseservice.service.AdminService;

@Component
@Path("login")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public final class LoginResource {
	
	@Autowired
	private AdminService adminService;
	
	@POST
	public Response login(LoginBean credentials){
		
		if(credentials.getPassword() == null || credentials.getUsername() == null)
			throw new UnathorizedException("Missing username or password");
		
		String token = adminService.login(credentials.getUsername(), credentials.getPassword());
		
		AccessBean accessBean = new AccessBean(token, "600");
		
		return Response.ok(accessBean).build();
	}

}