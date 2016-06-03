package com.home.mystorywriter;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.json.simple.JSONObject;
import com.google.gson.Gson;
import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.xml.bind.DatatypeConverter;

@SuppressWarnings("unused")
@MultipartConfig 
public class StoryServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final int IMG_WIDTH = 80;
	private static final int IMG_HEIGHT = 65;
	JSONObject json = new JSONObject();  
	String jsontostring=null;		
	String action =null;
	String ImageFolder;
	String DBpath;
	DAOdb db;
		
    //String images_folder_path= System.getProperty("catalina.base") + "/webapps/mystorywriter/images/";
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//ServletContext servletcontext = getServletContext();
		//String contextPath = servletcontext.getRealPath(File.separator);
		//DBpath = contextPath +File.separator + "WEB-INF"+File.separator+"db"+File.separator+"testdb.db";
		//ImageFolder = contextPath +File.separator + "WEB-INF"+File.separator+"images"+File.separator;
		db = (DAOdb)getServletContext().getAttribute("db");
		ImageFolder=(String)getServletContext().getAttribute("ImageFolder");
		System.out.println(ImageFolder);
		action = request.getParameter("action");
		if (action == null) action = "login";    
        switch (action) {
        case "storypic": upload(request,response);break;
        
        case "login":  login(request,response);break;
        
        case "createStory":  createStory(request,response);break; 
        
        case "passtitle":  passtitle(request,response);break;
        
        case "passbegin": passbegin(request,response);break;
        
        case "passmiddle": passmiddle(request,response);break;
        
        case "passend": passend(request,response);break;
        
        case "upload": upload(request,response);break;
        
        case "editprofile": profileEdit(request,response);break;
        
        case "editprofilepic": editprofilepic(request,response);break;
        
        case "register": register(request,response);break;
        
        default       :  homepage(request,response);
    	
    }
    
}//doPost method ends here.
	
//upload story pic method
	
private void upload(HttpServletRequest request, HttpServletResponse response) throws IOException{
	response.setContentType("application/json");
	response.setCharacterEncoding("utf-8");
	PrintWriter out = response.getWriter();
	Story S=null;
	String error;
	String image_contents = request.getParameter("contents");
	Integer storyid = Integer.parseInt(request.getParameter("storyid"));
	System.out.println(image_contents);
	System.out.println(storyid);
	/*DAOdb db = null;
	try {
	          db = new DAOdb(DBpath);
	    }catch (Exception e) {         
	         e.printStackTrace();
	    }	*/
		
//if((image_contents!=null) && (storyid!=null)){
System.out.println(image_contents + storyid);
image_contents = image_contents.substring("data:image/png;base64,".length());
//System.out.println(image_contents);
//byte[] bImg64 = image_contents.getBytes();
byte[] asBytes = Base64.decodeBase64(image_contents.getBytes());
System.out.println(new String(asBytes));
//byte[] decodedBytes = Base64.decodeBase64(bImg64);
//InputStream is = new ByteArrayInputStream(asBytes);
try{
   db.updateStoryPic(storyid, "png", asBytes);
   String url = "story.jsp";
   json.put("storyjsp", url);
   S=db.getStorybyStoryId(storyid);
   request.getSession().setAttribute("story", S);
//}
}catch(SQLException e){
	error= e.getMessage().toString();
	json.put("error",error);
	
}
jsontostring = json.toString();
out.write(jsontostring);

}
	
//login post method	
private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
	    response.setContentType("application/json");
   	    response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
	 
		String username = request.getParameter("name");
		String password = request.getParameter("password");
		
		/*if ((username.toLowerCase().equals("admin")) && (password.toLowerCase().equals("password"))){
			 RequestDispatcher rd = request.getRequestDispatcher("/rest/users/allUsers");   
			 rd.forward(request, response);  
		}*/
		
		//String a = System.getProperty("user.dir")+ "/git/mystorywriter/war/images/"+"defaultprofilepic.png";
                String a =ImageFolder+"defaultprofilepic.png";
		System.out.println(System.getProperty(a));
	    	
	    
		/*DAOdb db = null;
        try {
              db = new DAOdb(DBpath);
            } catch (Exception e) {
 
            e.printStackTrace();
        }*/
					
	    if(username!=null && password!=null){
		   LoginBean bean = new LoginBean(username, password);
			if (LoginValidator.validate(bean)) {
			User user = db.Authenticate(username, password);
			    if (user == null) {
	                String error = db.getLastError();
	                json.put("error", error);
	            }else {
	                request.getSession().setAttribute("user", user);
	               	Profile userprof = db.getProfilebyUsername(username);
	               	if(userprof!=null){
	               	      System.out.println(userprof.getFirstname() + " "+ userprof.getLastname() + "profile[ic:" +userprof.getProfpic() + ""+userprof.getEmail());
	               	      request.getSession().setAttribute("profile", userprof);
	               	   if(db.storyExists(username)){
	                      List <Story> stories = db.getStoriesbyUsername(username);
	                      System.out.println(stories.get(0) );
	               	      request.getSession().setAttribute("stories", stories);
	               	      Gson gson = new Gson();
	               	      String jsonStories = gson.toJson(stories);
	               	      System.out.println("jsonStories = " + jsonStories);
	               	      }else{
	               	    	 json.put("storycount", "none");
	               	      }
	               	      String url ="main.jsp";
	                      json.put("redirect",url);
	               	}else{
	               	     String error = db.getLastError();
		                 json.put("error", error);
	               	}
	                
	            }
		    }else{
			    System.out.println("Username or password cannot be validated.");
	            String error = "Username should be between 1 to 12 character and password should be between 6 and 15 characters.";
	            json.put("error",error);
		    }
		 }
	    // finally output the json string       
	    jsontostring = json.toString();
	    out.write(jsontostring);	
}
//createStory post method	
private void createStory(HttpServletRequest request, HttpServletResponse response) throws IOException{
	response.setContentType("application/json");
	response.setCharacterEncoding("utf-8");
	PrintWriter out = response.getWriter();
	    String storytitle= request.getParameter("storyTitle");
		String storybegin=request.getParameter("storyBegin");
		String storymiddle=request.getParameter("storyMiddle");
		String storyend=request.getParameter("storyEnd");
		/*DAOdb db = null;
        try {
              db = new DAOdb(DBpath);
            } catch (Exception e) {
             
             e.printStackTrace();
        }*/
		User user = (User) request.getSession().getAttribute("user");
	    if (user!=null)
	    {	    
		   Integer userid = user.getId();
		   System.out.println(userid);
		   if(storytitle!=null && storybegin!=null && storymiddle!=null && storyend!=null ){
				Story S = new Story();
				S.setId(null);
				S.setTitle(storytitle);
				S.setFirstPart(storybegin);
				S.setMiddlePart(storymiddle);
				S.setLastPart(storyend);
				S.setStorypic(null);
				S.setAuthorid(userid);
				db.addStory(S);		
			String url = "draw.jsp";
			request.getSession().setAttribute("story", S);
			List <Story> stories = db.getStoriesbyUsername(user.getUsername());
            request.getSession().setAttribute("stories", stories);
			json.put("redirect", url);
		   }
     }
	    jsontostring = json.toString();
	    out.write(jsontostring);	
}

private void passtitle(HttpServletRequest request, HttpServletResponse response) throws IOException{
	response.setContentType("application/json");
	response.setCharacterEncoding("utf-8");
	PrintWriter out = response.getWriter();
	request.getSession().setAttribute("passtitle", request.getParameter("storyTitle"));
	json.put("success", "success");
	jsontostring = json.toString();
	out.write(jsontostring);

}

private void  passbegin(HttpServletRequest request, HttpServletResponse response) throws IOException{
	response.setContentType("application/json");
	response.setCharacterEncoding("utf-8");
	PrintWriter out = response.getWriter();
	request.getSession().setAttribute("passbegin", request.getParameter("storyBegin"));
	json.put("success", "success");
	jsontostring = json.toString();
	out.write(jsontostring);	

}

private void passmiddle(HttpServletRequest request, HttpServletResponse response) throws IOException{
	response.setContentType("application/json");
	response.setCharacterEncoding("utf-8");
	PrintWriter out = response.getWriter();
	request.getSession().setAttribute("passmiddle", request.getParameter("storyMiddle"));
	json.put("success", "success");
	jsontostring = json.toString();
	out.write(jsontostring);

}

private void passend(HttpServletRequest request, HttpServletResponse response) throws IOException{
	response.setContentType("application/json");
	response.setCharacterEncoding("utf-8");
	PrintWriter out = response.getWriter();
	request.getSession().setAttribute("passend", request.getParameter("storyEnd"));
	json.put("success", "success");
	jsontostring = json.toString();
	out.write(jsontostring);
}

private void homepage(HttpServletRequest request, HttpServletResponse response) throws IOException{
	response.setContentType("application/json");
	response.setCharacterEncoding("utf-8");
	PrintWriter out = response.getWriter();
	json.put("storycomppage", "storycomp.jsp");
	jsontostring = json.toString();
	out.write(jsontostring);
}

private void  profileEdit(HttpServletRequest request, HttpServletResponse response) throws IOException{
	response.setContentType("application/json");
	response.setCharacterEncoding("utf-8");
	PrintWriter out = response.getWriter();
	Integer profileid = 0;
	String profid = request.getParameter("profileid");	
	String firstname = request.getParameter("fn");
	String lastname = request.getParameter("ln");
	String email = request.getParameter("email");
	/*
	DAOdb db = null;
    try {
          db = new DAOdb(DBpath);
        } catch (Exception e) {
        e.printStackTrace();
        }*/
    try{
        if(profid != null)
          profileid = Integer.parseInt(profid);
       }catch (NumberFormatException e){
    	profileid = 0;
       }
    if((firstname!=null) || (!firstname.isEmpty())){
		db.updateProfilefn(firstname, profileid);
	}
	
	if((lastname!=null) || (!lastname.isEmpty())){
		db.updateProfileln(lastname, profileid);
	}
	
	if((email!=null) || (!email.isEmpty())){
		db.updateProfileemail(email, profileid);
	}
	
	json.put("profileurl", "profile.jsp");
	jsontostring = json.toString();
	out.write(jsontostring);
    
}

/***
 * upload profile picture method
 * @throws IOException 
 * @throws ServletException */
private void editprofilepic(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
	response.setContentType("application/json");
	response.setCharacterEncoding("utf-8");
	PrintWriter out = response.getWriter();
	Integer profileid = 0;
	String error = null;
	String flash=null;
	String pid = request.getParameter("profilepicid");
	Part filepart = null;
	byte[] bytes;
	//HashMap<String, Object> map = new HashMap<String, Object>();
	/*DAOdb db = null;
	try {
          db = new DAOdb(DBpath);
        } catch (Exception e) {
        e.printStackTrace();
        }*/
    try{
        if(pid != null)
          profileid = Integer.parseInt(pid);
       }catch (NumberFormatException e){
    	profileid = 0;
       }
    try {
		  filepart = request.getPart("file");
		  String filename = getFileName(filepart);
		  Long size = filepart.getSize();
	      String contentType = filepart.getContentType();
	      InputStream filecontent = filepart.getInputStream();
	      if(filecontent.available()!= -1 && filepart.getSize()!=0L){
	    	bytes = IOUtils.toByteArray(filecontent);
	    	db.updateProfilepic(bytes, profileid);
	    	json.put("filename", filename);
            json.put("fileoriginalsize", size);
	        json.put("contenttype", contentType);
	        json.put("base64", new String(Base64.encodeBase64(bytes)));	       
	        }
		}catch (IOException|ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			error = e.getMessage().toString();
		}
	
    out.write(json.toString());
		
}

/**
 * Utility method to get file name from HTTP header content-disposition
 */
private String getFileName(Part part) {
    String contentDisp = part.getHeader("content-disposition");
    System.out.println("content-disposition header= "+contentDisp);
    String[] tokens = contentDisp.split(";");
    for (String token : tokens) {
        if (token.trim().startsWith("filename")) {
            return token.substring(token.indexOf("=") + 2, token.length()-1);
        }
    }
    return "";
}

/**
 * register a new user method
 **/

private void register(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException{
	response.setContentType("application/json");
	response.setCharacterEncoding("utf-8");
	PrintWriter out = response.getWriter();
	String error = null;
	String fn = request.getParameter("firstname");
	String ln = request.getParameter("lastname");
	String un = request.getParameter("username");
	String pwd = request.getParameter("password");
	String email = request.getParameter("email");
	/*DAOdb db = null;
	try {
	     db = new DAOdb(DBpath);
		    }catch (Exception e) {
		 e.printStackTrace();
		    }	*/
	
	if((fn!=null) && (ln!=null) && (un!=null) && (pwd!=null) && (email!=null)){
		
	//check for user name uniqueness	
	if(db.isUsernameAvailable(un)){
	
	//add User
    User U = new User(un,pwd);
	db.addUser(U);
	 
	 //add Profile
	Profile P = new Profile(fn,ln,email,U.getId());
	db.addProfile(P);
	
	db.updateUserprofileid(U.getUsername(),P.getId());
	
   if (U!= null) {
        request.getSession().setAttribute("user",U);
       	if(P!=null){
       	   request.getSession().setAttribute("profile", P);
       	   if(db.storyExists(U.getUsername())){
              List <Story> stories = db.getStoriesbyUsername(U.getUsername());
              request.getSession().setAttribute("stories", stories);
       	      }else{
       	    	 json.put("storycount", "No Stories");
       	      }
       	      
             
    }
       	json.put("redirect","main.jsp");
   	}
	
	}else{
		json.put("error", "Username already exists.Select another username");
	}
	
	}else{
		json.put("error", "One of fields on registration form is empty");
	}
	
	jsontostring = json.toString();
	out.write(jsontostring);
}

/*******************************************************************************************************************************************************/
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//ServletContext servletcontext = getServletContext();
	//String contextPath = servletcontext.getRealPath(File.separator);
	//DBpath = contextPath +File.separator + "WEB-INF"+File.separator+"db"+File.separator+"testdb.db";
	//ImageFolder = contextPath + File.separator + "WEB-INF"+File.separator+"images"+File.separator;
	//System.out.println(contextPath + DBpath +ImageFolder);
	db = (DAOdb)getServletContext().getAttribute("db");
	ImageFolder=(String)getServletContext().getAttribute("ImageFolder");
	
		String action = request.getParameter("action");   
        if (action == null) action = "main";
        switch (action) {
            case "main": gethomepage(request,response);break;
            case "createStory": getcreateStory(request,response);break;
            case "canvas":getcanvas(request,response);break;
            case "getStories":getStories(request,response);break;
            case "image": getprofileimage(request,response);break;
            case "storypdf" : storypdf(request,response);break;
            case "login":  getlogin(request,response);break;
            case "logout": logout(request,response);break;
            case "register":getregister(request,response); break; 
            case "profile": request.getRequestDispatcher("profile" + ".jsp").forward(request,response);break;
            case "storyid": getStoryDetails(request,response);break;
            default: action = "main";request.getRequestDispatcher("main" + ".jsp").forward(request,response);
        }
        
        
        
    }
	
private void gethomepage (HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException{
	response.setContentType("application/json");
    response.setCharacterEncoding("utf-8");
    PrintWriter out = response.getWriter();
	json.put("redirect", "main.jsp");
	out.write(json.toString());
}

private void getregister (HttpServletRequest request,HttpServletResponse response) throws IOException{
	 response.setContentType("application/json");
	 response.setCharacterEncoding("utf-8");
	 PrintWriter out = response.getWriter();
	 json.put("redirect", "register.jsp");
	 out.write(json.toString());
}

private void getlogin (HttpServletRequest request,HttpServletResponse response) throws IOException{
	response.setContentType("application/json");
	response.setCharacterEncoding("utf-8");
	PrintWriter out = response.getWriter();
	json.put("redirect", "login.html");
	out.write(json.toString());	
}

private void logout (HttpServletRequest request,HttpServletResponse response) throws IOException{
	response.setContentType("application/json");
	response.setCharacterEncoding("utf-8");
	PrintWriter out = response.getWriter();
	request.getSession().invalidate();
	json.put("redirect", "login.html");
 	out.write(json.toString());	
}

private void getcreateStory (HttpServletRequest request,HttpServletResponse response) throws IOException{
	response.setContentType("application/json");
	response.setCharacterEncoding("utf-8");
	PrintWriter out = response.getWriter();
	json.put("redirect", "storycomp.jsp");
	out.write(json.toString());	
}


private void getStoryDetails(HttpServletRequest request,HttpServletResponse response) throws IOException{
	    response.setContentType("application/json");
	    response.setCharacterEncoding("utf-8");
	    PrintWriter out = response.getWriter();
		Integer storyid = Integer.parseInt(request.getParameter("id"));
		Story S = null;
		/*DAOdb db = null;
	    try {
	          db = new DAOdb(DBpath);
	        } catch (Exception e) {
	         
	         e.printStackTrace();
	    }	*/
	    
	   S = db.getStorybyStoryId(storyid);
	   request.getSession().setAttribute("story", S);
	   json.put("redirect", "story.jsp");
	   out.write(json.toString());	
	}
	
private void getcanvas(HttpServletRequest request, HttpServletResponse response)throws IOException{
	       
	    	Story S = null;
		    String storyid  = request.getParameter("for");
	        Integer sid = 0;
	      
	        try
	        {
	            if(storyid != null)
	              sid = Integer.parseInt(storyid);
	        }
	        catch (NumberFormatException e)
	        {
	        	sid = 0;
	        	
	        }
	        
	        /*DAOdb db = null;
		    try {
		          db = new DAOdb(DBpath);
		        } catch (Exception e) {
		         
		         e.printStackTrace();
		    }	*/
		    
		    S = db.getStorybyStoryId(sid);
		    byte[] storypic = S.getStorypic();
	        if (storypic == null) {
	            String image_string = "defaultstorypic.png";
	            //String filepath = System.getProperty("user.dir")+ "/git/mystorywriter/war/images/"+image_string;
                      String filepath = ImageFolder +image_string;
	            try {
	    	    	File deffile = new File(filepath);
	    	        FileInputStream inStream = new FileInputStream(deffile.getAbsolutePath());
	    	        // obtains response's output stream
	    	        OutputStream outStream = response.getOutputStream();
	    	         
	    	        byte[] buffer = new byte[4096];
	    	        int bytesRead = -1;
	    	         
	    	        while ((bytesRead = inStream.read(buffer)) != -1) {
	    	            outStream.write(buffer, 0, bytesRead);
	    	        }
	    	         
	    	        inStream.close();
	    	        outStream.close();   
	            }catch(Exception e){
	            	e.getMessage().toString();
	            }
	           
	        }else{        
	           /**
	            StringBuilder sb = new StringBuilder();
	        	sb.append("data:image/png;base64,");
	        	sb.append(" ");
	        	sb.append(java.util.Base64.getEncoder().encodeToString(storypic));
	        	String newString = sb.toString();
	        	System.out.println("base64encoded:" +newString);	
	        	json.put("redirect", newString);
	        	out.write(json.toString());	
	        	//response.setContentType("image/png");
	        	//response.getWriter().println(newString);
	        	 */


	        	
	        	//byte[] imageBytes = java.util.Base64.getEncoder().encode(storypic);check
	        	String imageString = new String (Base64.encodeBase64(storypic));
	        	byte[]decodedbytes = Base64.decodeBase64(imageString.getBytes());
	        	response.setContentType("image/png");
	        	response.setContentLength(decodedbytes.length);
	        	response.getOutputStream().write(decodedbytes);
	        	     	
	        	
	        } 
	    }
	
	
private void storypdf(HttpServletRequest request, HttpServletResponse response) {
    
    	Story S = null;
	    String storyid  = request.getParameter("for");
        Integer sid = 0;
        try
        {
            if(storyid != null)
              sid = Integer.parseInt(storyid);
        }
        catch (NumberFormatException e)
        {
        	sid = 0;
        }
        
    	/*DAOdb db = null;
	    try {
	          db = new DAOdb(DBpath);
	        } catch (Exception e) {
	         
	         e.printStackTrace();
	    }*/	
	    
	    S = db.getStorybyStoryId(sid);
	    
	    String filename = "" + S.getId()+S.getAuthorid()+".pdf";
  	    //String oldfilepath = System.getProperty("user.dir")+ "/git/mystorywriter/war/images/"+filename;
            String oldfilepath = ImageFolder +filename;
  	    File oldfile;
		try {
			oldfile = new File(oldfilepath).getCanonicalFile();
			if(oldfile.exists() && oldfile.isFile()) {
				oldfile.delete();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String filepath = CreateCanvasImage(S.getId(),"png");
		
	    
	    StoryPdf pdf =new StoryPdf(S.getTitle(),S.getFirstPart(),S.getMiddlePart(),S.getLastPart());
	    pdf.createPdf(oldfilepath, filepath);
	    
	    try {
	    	// reads input file from an absolute path
	       // String filePath = getServletContext().getRealPath("/WebContent/images/temp.pdf");
	        File downloadFile = new File(oldfilepath).getCanonicalFile();
	        FileInputStream inStream = new FileInputStream(downloadFile);
	        
	        // obtains ServletContext
	        ServletContext context = getServletContext();
	         
	        // gets MIME type of the file
	        String mimeType = context.getMimeType(oldfilepath);
	        if (mimeType == null) {        
	            // set to binary type if MIME mapping not found
	            mimeType = "application/octet-stream";
	        }
	        System.out.println("MIME type: " + mimeType);
	         
	        // modifies response
	        response.setContentType(mimeType);
	        response.setContentLength((int) downloadFile.length());
	         
	        // forces download
	        String headerKey = "Content-Disposition";
	        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
	        response.setHeader(headerKey, headerValue);
	        
	        // obtains response's output stream
	        OutputStream outStream = response.getOutputStream();
	         
	        byte[] buffer = new byte[4096];
	        int bytesRead = -1;
	         
	        while ((bytesRead = inStream.read(buffer)) != -1) {
	            outStream.write(buffer, 0, bytesRead);
	        }
	         
	        inStream.close();
	        outStream.close();     
	        
        } catch (IOException ioe) {
            request.setAttribute("flash", ioe.getMessage());
        }
    }
	
	
  private String CreateCanvasImage(Integer storyid, String mimetype){
	    
		
		Story S = null;
    	String filepath = null;
    	   	
    	/*DAOdb db = null;       
	    try {
	          db = new DAOdb(DBpath);
	        } catch (Exception e) {
	         
	         e.printStackTrace();
	    }*/	
	    
	    S = db.getStorybyStoryId(storyid);
	    byte[] storypic = S.getStorypic();
        if (storypic == null) {
        	//filepath = System.getProperty("user.dir")+ "/git/mystorywriter/war/images/"+"defaultstorypic.png";
                filepath = ImageFolder +"defaultstorypic.png";
        }else{
              try{
            	  String filename = "" + S.getId()+S.getAuthorid()+"canvas"+".png";
            	  //filepath = System.getProperty("user.dir")+ "/git/mystorywriter/war/images/"+ filename;
                    filepath = ImageFolder + filename;
            	  String imageString = new String(Base64.encodeBase64(storypic));
        	      byte[]decodedbytes = Base64.decodeBase64(imageString.getBytes());
        	      // convert decoded byte array to BufferedImage
        	      InputStream in = new ByteArrayInputStream(decodedbytes);
        	      BufferedImage bImageFromConvert = ImageIO.read(in);
        	      ImageIO.write(bImageFromConvert, "png", new File(filepath).getCanonicalFile());
        	     }catch(IOException e) {
        	      e.getMessage().toString();
                 }
        }
        
		return filepath;
	}
	
	
	/**
	 *get profile image method
	 */
	
private void getprofileimage(HttpServletRequest request,HttpServletResponse response) throws IOException{
	   response.setContentType("application/json");
       response.setCharacterEncoding("utf-8");
       PrintWriter out = response.getWriter();
    	
    	Profile P =null;
    	String profileid  = request.getParameter("for");
        Integer pid = 0;
      
        try
        {
          if(profileid != null)
            pid = Integer.parseInt(profileid);
        }
        catch (NumberFormatException e)
        {
        	pid = 0;
        }
        
        /*DAOdb db = null;
	    try {
	          db = new DAOdb(DBpath);
	        } catch (Exception e) {
	         
	         e.printStackTrace();
	    }*/	
	    
	    P = db.getProfilebyProfileId(pid);
	    byte[] profilepic = P.getProfpic();
try{
	    	
        if (profilepic == null) {
            //response.setStatus(404);
        	//String defaultfilepath = System.getProperty("user.dir")+ "/git/mystorywriter/war/images/"+"defaultprofilepic.png";
            String defaultfilepath = ImageFolder +"defaultprofilepic.png";
        	BufferedImage oldbi =  ImageIO.read(new File(defaultfilepath));
    		int type = oldbi.getType();
  	        BufferedImage newbi = resizeImage(oldbi, type);
        	ImageIO.write(newbi, "png", new File(defaultfilepath)); 
    		byte[] imageInbytes = bufferedImagetobytearray(newbi);
    		//response.getOutputStream().write( imageInbytes );
    		String base64 = new String(java.util.Base64.getEncoder().encode(imageInbytes));
    		String base64string = "data:" + "image/png" + ";base64,"+ base64 ;
    		//json.put("contenttype", "image/png");
   	        json.put("base64String", base64string);	 
    		//json.put("base64", defaultfilepath);	
   	        }
        
         else{     
        	
        	String filename = "" + P.getId()+P.getUserId()+"profile";
       	        //String filepath = System.getProperty("user.dir")+ "/git/mystorywriter/war/images/"+ filename;
            String filepath = ImageFolder + filename;
        	String imageString = new String(Base64.encodeBase64(profilepic));
        	byte[]decodedbytes = Base64.decodeBase64(imageString.getBytes());
           	InputStream in = new ByteArrayInputStream(decodedbytes);
  	        BufferedImage bImageFromConvert = ImageIO.read(in);
  	        ImageIO.write(bImageFromConvert, "png", new File(filepath+".png"));
            BufferedImage oldbi =  ImageIO.read(new File(filepath+".png"));
    		int type = oldbi.getType();
  	        BufferedImage newbi = resizeImage(oldbi, type);
  		    ImageIO.write(newbi, "png", new File(filepath+".png")); 
  		    //json.put("filepath", filepath);
  		    byte[] imageInbytes = bufferedImagetobytearray(newbi);
  		  	//response.getOutputStream().write( imageInbytes );
  		  String base64 = new String(java.util.Base64.getEncoder().encode(imageInbytes));
  		  String base64string = "data:" + "image/png" + ";base64,"+ base64 ;
  		  json.put("base64String", base64string);	 
  		  // json.put("contenttype", "image/png");
 	      //  json.put("base64", new String(java.util.Base64.getEncoder().encode(imageInbytes)));	 
  		 // json.put("base64", filepath);	
  		    
  		  	
        }
  		      			
   }catch (IOException e) {
        	e.getMessage().toString();
        }
        
       out.write(json.toString());
    }
	
/**
 * Resize image function	
 */

private static BufferedImage resizeImage(BufferedImage originalImage, int type){
		BufferedImage resizedImage = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
		g.dispose();
			
		return resizedImage;
}

@SuppressWarnings("unused")
private static byte[] bufferedImagetobytearray(BufferedImage bi){
	byte[] imageInByte = null;
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	try {
		ImageIO.write(bi, "png", baos );
		baos.flush();
		imageInByte = baos.toByteArray();
		baos.close();
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		
	}
	
	return imageInByte;
}

private void getStories(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	
	String un = request.getParameter("user");
	/*DAOdb db = null;
	 try {
         db = new DAOdb(DBpath);
       } catch (Exception e) {
        
        e.printStackTrace();
       }*/	
	if(db.storyExists(un)){
        List <Story> stories = db.getStoriesbyUsername(un);
        System.out.println(stories.get(0) );
 	    request.getSession().setAttribute("stories", stories);
 	    Gson gson = new Gson();
 	    String jsonStories = gson.toJson(stories);
 	    System.out.println("jsonStories = " + jsonStories);
 	    request.setAttribute("userstories", stories);
 	 }
	//out.write(json.toString());
	
}
     
  
}
