package com.home.mystorywriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.moxy.json.*;
import com.sun.jersey.json.*;
import com.google.gson.Gson;
import javax.ws.rs.core.Application;
import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;

@Path("/users")

public class UserWS {
private ServletContext context ;
DAOdb db;
public UserWS() {
    System.out.println("Inside Constructor " + context); 
}
@Context
public void setServletContext(ServletContext context) {
    System.out.println("servlet context set here");
    this.context = context;
    db = (DAOdb)context.getAttribute("db");
}

@GET
@Produces(MediaType.APPLICATION_JSON)
public Response getusers() {
	 GenericEntity<List<User>> userlist = new GenericEntity<List<User>>(db.getUserListOfAllUsers()) {
    };
    return Response.ok(userlist).build(); 
    
	}

@GET
@Produces(MediaType.APPLICATION_JSON)
@Path("byusername/{username}")
public Response getUserbyusername(@PathParam("username") String username) {
	User user = db.getUserbyUsername(username);
	System.out.println("user"+ user);
	//Integer id = user.getId();
	//GenericEntity<List<Story>> stories = new GenericEntity<List<Story>>(db.getStoriesbyAuthorid(id)) {
   // };
   // return Response.status(200).entity(stories).build(); 
return Response.ok(user).build(); 
    
	}

@GET
@Produces(MediaType.APPLICATION_XML)
@Path("/getusersasxml")
public Response getusersasxml() {
	 GenericEntity<List<User>> userlist = new GenericEntity<List<User>>(db.getUserListOfAllUsers()) {
    };
    return Response.ok(userlist).build(); 
    
	}

} 


