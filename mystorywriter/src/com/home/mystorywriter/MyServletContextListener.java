package com.home.mystorywriter;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.ServletContext;

/**
 * Application Lifecycle Listener implementation class MyServletContextListener
 *
 */
@WebListener
public class MyServletContextListener implements ServletContextListener {
    private ServletContext servletcontext;
    private String contextPath;
    private String DBpath;
    private String ImageFolder;
    private DAOdb db = null;
   
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("ServletContextListener destroyed");
	}

        //Run this before web application is started
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("ServletContextListener started");	
		servletcontext =sce.getServletContext();
		contextPath = servletcontext.getRealPath(File.separator);
		DBpath = contextPath +File.separator + "WEB-INF"+File.separator+"db"+File.separator+"testdb.db";
		ImageFolder = contextPath +File.separator + "WEB-INF"+File.separator+"images"+File.separator;
		System.out.println(contextPath + DBpath +ImageFolder);
		try {
		          db = new DAOdb(DBpath);
		    }catch (Exception e) {         
		         e.printStackTrace();
		    }	
		sce.getServletContext().setAttribute("db", db);
		sce.getServletContext().setAttribute("ImageFolder", ImageFolder);
		
		
	}
	
}
