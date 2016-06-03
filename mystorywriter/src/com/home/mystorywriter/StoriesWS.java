package com.home.mystorywriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.moxy.json.*;
import com.sun.jersey.json.*;
import com.google.gson.Gson;
import javax.ws.rs.core.Application;
import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;

@Path("/stories")

public class StoriesWS {
private ServletContext context ;
DAOdb db;
public StoriesWS() {
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
public Response getstories() {
	GenericEntity<List<Story>> storylist = new GenericEntity<List<Story>>(db.getStoryListWithoutStorypic()) {
    };
    return Response.ok(storylist).build(); 
}

@GET
@Produces(MediaType.APPLICATION_XML)
@Path("/getstoriesasxml")
public Response getstoriesinxmlformat() {
	GenericEntity<List<Story>> storylist = new GenericEntity<List<Story>>(db.getStoryListWithoutStorypic()) {
    };
    return Response.ok(storylist).build(); 
}

@GET
@Produces(MediaType.APPLICATION_JSON)
@Path("/byid/{storyid}")
public Response getstorybyid(@PathParam("storyid") Integer storyid) {
	Story story = db.getStorybyStoryId(storyid);
	//String test= "The variable storyid value is " + storyid;
    return Response.ok(story).build(); 
} 

@GET
@Produces({"application/json"})
@Path("/byusername/{username}")
public Response getstoriesbyusername(@PathParam("username")String username) {
	    User user = db.getUserbyUsername(username);
	    System.out.println(user);
		List<Story> newList = new ArrayList<Story>();
		List<Story> oldList = db.getStoryListWithoutStorypic();
		Iterator<Story> oldListIterator = oldList.iterator();
		while(oldListIterator.hasNext()){
			Story story = oldListIterator.next();
			if (story.getAuthorid().equals(user.getId())){
				newList.add(story);
				//System.out.println(story.getAuthorid() + "is equal to" + authorid  );
				}		
		}	
		System.out.println(newList.size());
		GenericEntity<List<Story>> stories = new GenericEntity<List<Story>>(newList) {
	    };
	    return Response.ok(stories).build(); 
}
} 
