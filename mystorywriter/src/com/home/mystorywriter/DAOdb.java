package com.home.mystorywriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.StatementBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.table.TableUtils;
import com.j256.ormlite.support.ConnectionSource;

@SuppressWarnings("unused")
public class DAOdb {
    
	//private static String DB_PATH = System.getProperty("user.dir")+ "git/mystorywriter/db/testdb.db";
    //   private static String DB_PATH = System.getProperty("catalina.base") + "/webapps/mystorywriter/db/testdb.db";
	//private final static String DATABASE_URL = "jdbc:sqlite:"+ DB_PATH;
	// we are using the in-memory H2 database "jdbc:sqlite:testdb.db";	
	private ConnectionSource connectionSource;
	private Dao<User, Integer> userDAO;
	private Dao<Profile,Integer> profileDAO;
	private Dao<Story, Integer> storyDAO;
	private String lastError;
	private static String DATABASE_URL;
    
	
	public DAOdb(String DBpath){
		connectionSource = null;
		DATABASE_URL = "jdbc:sqlite:"+ DBpath;
	try {
		// create our data-source for the database
		connectionSource = new JdbcConnectionSource(DATABASE_URL);
		userDAO = DaoManager.createDao(connectionSource, User.class);
		//TableUtils.createTable(connectionSource, User.class);
		profileDAO = DaoManager.createDao(connectionSource, Profile.class);
		//TableUtils.createTable(connectionSource, Profile.class);
		storyDAO = DaoManager.createDao(connectionSource, Story.class);
		//TableUtils.createTable(connectionSource, Story.class);
		}catch(SQLException e){
	    	System.out.println(e.getMessage());
	    }
}
	
	/**
	 * DAO Functions to fetch data
	 */
	
	 public List<Story> getStoryListWithoutStorypic(){
			List<Story> storyList = new ArrayList<Story>();
			GenericRawResults<String[]> rawResults;
			try {
				rawResults = storyDAO.queryRaw(
				    "select id,title,firstpart,middlepart,lastpart,authorid from stories order by id");
				for (String[] resultArray : rawResults) {
					  /*System.out.println("author-id " + resultArray[5] +"Title" +resultArray[1]+" Firstpart "
					    + resultArray[2] + "Middlepart" +resultArray[3] +"Lastpart" +resultArray[4]+"id"+resultArray[0]);*/
					  Story story = new Story(Integer.parseInt(resultArray[0]),resultArray[1],resultArray[2],resultArray[3],resultArray[4],Integer.parseInt(resultArray[5]));
					  storyList.add(story);				  
					}
				
			    rawResults.close();		 
					 
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return storyList;
					 
		 }
	
	
	 public void addStory(Story S) {
		 try{
			 storyDAO.create(S);
	         lastError = null;
	        } catch (Exception e) {
	        	lastError = e.getMessage().toString();
	        }
	    }
	 
	 public Story getStorybyStoryId(Integer storyid){
		 Story story = null;
		  try{				
			    story = storyDAO.queryForId(storyid);			    
				System.out.println(story);
				// there should be 1 result
			}catch(Exception e){
					System.out.println(e.getMessage().toString());
					lastError = e.getMessage().toString();
				}
		 return story;
		 
	 }
	 
	 public void updateStoryPic(int storyid, String mime, byte[] barray) throws SQLException{
		 try{
         UpdateBuilder<Story, Integer> updateBuilder = storyDAO.updateBuilder();
         updateBuilder.updateColumnValue(Story.PICTURE_FIELD_NAME, barray);
         updateBuilder.where().eq("id",storyid);
         updateBuilder.update();
		 }catch(Exception e){
			 lastError = e.getMessage().toString();
		 }
	 }
	 
	 public List<Story> getStoriesbyUsername(String username) {
		 String uname =username;
		 User user = this.getUserbyUsername(username);
		 System.out.println(user.getUsername());
		 Integer uid =user.getId();
		 List<Story> storyList=null;
		 try{
			    GenericRawResults<String[]> stories = storyDAO.queryRaw("select count(*) from stories where authorid =" + uid);
		   	     // there should be 1 result
				List<String[]> results = stories.getResults();
				// the results array should have 1 value
				Integer storycount = results.size();
				// this should print the number of orders that have this account-id
				if(storycount<=0){
					System.out.println("No story exist for " +username);
				  }else{
					   storyList = storyDAO.queryBuilder().where().eq(Story.AUTHORID_FIELD_NAME, uid).query();
					   System.out.println(storyList.get(0));
				  }
				}catch(SQLException e){
					lastError = e.getMessage().toString();
				}
				 
		  return storyList;
	    }
	 
	 
	 public List<Story> getStoriesbyAuthorid(Integer id) {
		 Integer uid =id;
		 List<Story> storyList=null;
		 try{
			    GenericRawResults<String[]> stories = storyDAO.queryRaw("select count(*) from stories where authorid =" + uid);
		   	     // there should be 1 result
				List<String[]> results = stories.getResults();
				// the results array should have 1 value
				Integer storycount = results.size();
				// this should print the number of orders that have this account-id
				if(storycount<=0){
					System.out.println("No story exist for authorid " +uid);
				  }else{
					   storyList = storyDAO.queryBuilder().where().eq(Story.AUTHORID_FIELD_NAME, uid).query();
					   System.out.println(storyList.get(0));
				  }
				}catch(SQLException e){
					lastError = e.getMessage().toString();
				}
				 
		  return storyList;
	    }
	 
	 
	 
	 
	 public boolean storyExists(String username){
		 String uname =username;
		 User user = getUserbyUsername(uname);
		 Integer uid =user.getId();
		 boolean isStory = false;
		 try{
			    GenericRawResults<String[]> stories = storyDAO.queryRaw("select count(*) from stories where authorid =" + uid);
		   	     // there should be 1 result
				List<String[]> results = stories.getResults();
				// the results array should have 1 value
				String[] resultarray = results.get(0);
				Integer storycount = Integer.parseInt(resultarray[0]);
				// this should print the number of orders that have this account-id
				if(storycount<=0){
					System.out.println("No story exist for " +username);
					isStory=false;
				  }else{
					  isStory=true;
				  }
				}catch(SQLException e){
					lastError = e.getMessage().toString();
				}
		return isStory;
	 }
	 
	 public void addUser(User U) {
		 try{
			 userDAO.create(U);
	         lastError = null;
	        } catch (Exception e) {
	        	lastError = e.getMessage().toString();
	        }
	    } 
	 
	 public void updateUserprofileid(String username, Integer profileid){
		 try{
         UpdateBuilder<User, Integer> updateBuilder = userDAO.updateBuilder();
         updateBuilder.updateColumnValue(User.PROFILE_ID, profileid);
         updateBuilder.where().eq(User.NAME_FIELD_NAME , username);
         updateBuilder.update();
		 }catch(Exception e){
			 lastError = e.getMessage().toString();
		 }
	 }
	 
	 public User getUserbyUsername(String username) {
		 // find out how many users are there with the same username
		String uname = username;
		User user = null;
		try{
		long usercount= userDAO.queryRawValue("select count(*) from Users where username = ?", uname); 
		// there should be 1 result
		if(usercount<=0){
			lastError="'User does not exist by username'+' '+uname";
		  }else if(usercount>1){
			lastError="'More than one user exist by same username'+' '+ uname"; 
		  }else{
			  
			  List<User> userList =
				      userDAO.queryBuilder().where()
				         .eq(User.NAME_FIELD_NAME, uname)
				         .query();
			  user = userList.get(0);
		  }
		}catch(Exception e){
			System.out.println(e.getMessage().toString());
			lastError = e.getMessage().toString();
		}
		 
		return user;
	  }
	 
	 public List<User> getUserListOfAllUsers(){
		 List<User> userList = null;
		 
		 try{
				userList = userDAO.queryForAll();
				// there should be 1 result
			}catch(Exception e){
					System.out.println(e.getMessage().toString());
					lastError = e.getMessage().toString();
				}
		 return userList;
	 }
	 
	 
	 public void addProfile(Profile P) {
		 try{
			 profileDAO.create(P);
	         lastError = null;
	        } catch (Exception e) {
	        	lastError = e.getMessage().toString();
	        }
	    } 
	 
	 public List<Profile> getProfileListOfAllUsers(){
		 List<Profile> profileList = null;
		 
		 try{
				profileList = profileDAO.queryForAll();
				// there should be 1 result
			}catch(Exception e){
					System.out.println(e.getMessage().toString());
					lastError = e.getMessage().toString();
				}
		 return profileList;
		 
	 }
	 
	 public List<Profile> getProfileListWithoutPic(){
		List<Profile> profileList = new ArrayList<Profile>();
		
		GenericRawResults<String[]> rawResults;
		try {
			rawResults = profileDAO.queryRaw(
			    "select userid,firstname,lastname,email,id from profiles order by userid");
			for (String[] resultArray : rawResults) {
				 
				  System.out.println("User-id " + resultArray[0] + " FN "
				    + resultArray[1] + "LN" +resultArray[2] +"email" +resultArray[3]+"id"+resultArray[4]);
				  Profile newprofile = new Profile(Integer.parseInt(resultArray[4]),resultArray[1],resultArray[2],resultArray[3],Integer.parseInt(resultArray[0]));
				  profileList.add(newprofile);				  
				}
			
		    rawResults.close();		 
				 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return profileList;
				 
	 }
	 
	 public Profile getProfilebyProfileId(Integer profileid){
		 Profile profile = null;
		 
		 try{
				profile = profileDAO.queryForId(profileid);
				// there should be 1 result
			}catch(Exception e){
					System.out.println(e.getMessage().toString());
					lastError = e.getMessage().toString();
				}
		 return profile;
		 
	 }
	 
	 
	 public void updateProfilefn(String firstname,Integer profileid) {
		 try{
			 UpdateBuilder<Profile, Integer> updateBuilder = profileDAO.updateBuilder();
			 updateBuilder.updateColumnValue(Profile.FIRSTNAME_FIELD_NAME, firstname);
			 updateBuilder.where().eq("id",profileid);
			 updateBuilder.update();
	         lastError = null;
	        } catch (Exception e) {
	        	lastError = e.getMessage().toString();
	        }
		 
	 }
	 
	 public void updateProfileln(String lastname,Integer profileid) {
		 try{
			 UpdateBuilder<Profile, Integer> updateBuilder = profileDAO.updateBuilder();
			 updateBuilder.updateColumnValue(Profile.LASTNAME_FIELD_NAME, lastname);
			 updateBuilder.where().eq("id",profileid);
			 updateBuilder.update();
	         lastError = null;
	        } catch (Exception e) {
	        	lastError = e.getMessage().toString();
	        }
		 
	 }
	 
	 public void updateProfileemail(String email,Integer profileid) {
		 try{
			 UpdateBuilder<Profile, Integer> updateBuilder = profileDAO.updateBuilder();
			 updateBuilder.updateColumnValue(Profile.EMAIL_FIELD_NAME, email);
			 updateBuilder.where().eq("id",profileid);
			 updateBuilder.update();
	         lastError = null;
	        } catch (Exception e) {
	        	lastError = e.getMessage().toString();
	        }
		 
	 }
	 
	 public void updateProfilepic(byte[]picbytes,Integer profileid) {
		 try{
			 UpdateBuilder<Profile, Integer> updateBuilder = profileDAO.updateBuilder();
			 updateBuilder.updateColumnValue(Profile.PROFILEPIC_FIELD_NAME, picbytes);
			 updateBuilder.where().eq("id",profileid);
			 updateBuilder.update();
	         lastError = null;
	        } catch (Exception e) {
	        	lastError = e.getMessage().toString();
	        }
		 
	 }
	 
	 public Profile getProfilebyUsername(String username) {
		 // find out how many users are there with the same username
		String uname = username;
		Profile profile =null;
		User user = getUserbyUsername(uname);
		Integer uid = user.getId();
		try{
     		// find out how many orders account-id #10 has
			GenericRawResults<String[]> rawResults = profileDAO.queryRaw("select count(*) from profiles where userid ="+uid);
			// there should be 1 result
			List<String[]> results = rawResults.getResults();
			// the results array should have 1 value
			Integer profcount = results.size();
			// this should print the number of orders that have this account-id
			System.out.println("profiles with userid 1 has " + profcount + "profiles");

		if( profcount <= 0){
			lastError="'Profile does not exist by username'+' '+ username";
		}else if(profcount>1){
			lastError="'More than one profile exist by same username'+' '+ username";
		}else{
			List<Profile> profileList = profileDAO.queryBuilder().where().eq(Profile.USERID_FIELD_NAME, uid).query();
			profile = profileList.get(0);
		     }
		}catch(SQLException e){
			lastError = e.getMessage().toString();
		}
		 
		return profile;
	  }
	 
	 public Profile getProfilebyUserid(Integer userid) {
		Profile profile =null;
		Integer uid = userid;
		try{
     		// find out how many orders account-id #10 has
			GenericRawResults<String[]> rawResults = profileDAO.queryRaw("select count(*) from profiles where userid ="+uid);
			// there should be 1 result
			List<String[]> results = rawResults.getResults();
			// the results array should have 1 value
			Integer profcount = results.size();
			// this should print the number of orders that have this account-id
			System.out.println("profiles with userid 1 has " + profcount + "profiles");

		if( profcount <= 0){
			lastError="'Profile does not exist by username'+' '+ username";
			System.out.println("Iaminprofcount<=0");
		}else if(profcount>1){
			lastError="'More than one profile exist by same username'+' '+ username";
			System.out.println("Iaminprofcount>1");
		}else{
			List<Profile> profileList = profileDAO.queryBuilder().where().eq(Profile.USERID_FIELD_NAME, uid).query();
			profile = profileList.get(0);
			System.out.println(profile);
		     }
		}catch(SQLException e){
			lastError = e.getMessage().toString();
		}
		 
		return profile;
	  }
	 
	 
	 
	 
	 public User Authenticate(String un,String pwd){
		 
		 String uname=un;
		 String passwd=pwd;
		 User user = null;
		 
		 try{
				long usercount= userDAO.queryRawValue("select count(*) from Users where username = ? and password = ?", uname,passwd); 
				// there should be 1 result
				if(usercount<=0){
					lastError="User does not exist by username "+uname;
				  }else if(usercount>1){
					lastError="More than one user exist by same username "+ uname; 
				  }else{
					  
					  List<User> userList =
						      userDAO.queryBuilder().where()
						         .eq(User.NAME_FIELD_NAME, uname)
						         .and()
						         .eq(User.PASSWORD_FIELD_NAME,passwd)
						         .query();
					  user = userList.get(0);
				  }
				}catch(Exception e){
					System.out.println(e.getMessage().toString());
					lastError = e.getMessage().toString();
				}
				 
				return user;
		    }
	 

			 
	public void close() {
		 if (connectionSource != null) {
				try {
					connectionSource.close();
				} catch (SQLException e) {
					lastError = e.getMessage().toString();
				}
			}
	    }
	
	 public String getLastError() { return lastError; }
	 
	 public boolean isUsernameAvailable(String username){
		 String uname = username;
		 User user = null;
		 boolean result = true;
	     try{
			long usercount= userDAO.queryRawValue("select count(*) from Users where username = ?", uname); 
			// there should be 1 result
			if(usercount<=0){
				result= true;
			  }else{	
				result= false;
			  }
	      }catch(Exception e){
	    	  lastError = e.getMessage().toString();
	      }
	     
	     return result;
		 
	  }
	
}