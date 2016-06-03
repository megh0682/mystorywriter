package com.home.mystorywriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.PathParam;
import org.glassfish.jersey.moxy.json.*;
import com.sun.jersey.json.*;
import com.google.gson.Gson;
import javax.ws.rs.core.Application;
import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;
@Path("/profiles")

public class ProfilesWS {
private ServletContext context ;
DAOdb db;
public ProfilesWS() {
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
public Response getprofiles() {
	//List<Profile> userlist = db.getProfileListWithoutPic();
    GenericEntity<List<Profile>> profilelist = new GenericEntity<List<Profile>>(db.getProfileListWithoutPic()) {
    };
    //Gson gson = new Gson();
	//String jsonUsers = gson.toJson(userlist);
    return Response.ok(profilelist).build(); 
    
	}

@GET
@Produces(MediaType.APPLICATION_JSON)
@Path("byuserid/{userid}")
public Response getprofilebyUserid(@PathParam ("userid") Integer userid) {
	Profile profile = db.getProfilebyUserid(userid);
	System.out.println("profile" + profile);
	System.out.println("profile.firstname" + profile.getFirstname());
	Profile newProfile = new Profile();
	newProfile.setId(profile.getId());
	newProfile.setFirstname(profile.getFirstname());
	newProfile.setLastname(profile.getLastname());
	newProfile.setEmail(profile.getEmail());
	newProfile.setUserId(profile.getUserId());
	return Response.ok(newProfile).build(); 
} 


@GET
@Produces(MediaType.APPLICATION_XML)
@Path("/getprofilesasxml")
public List<Profile> getprofilesinXML() {
	List<Profile> profilelist = db.getProfileListWithoutPic();
    return profilelist; 
  	}
}
