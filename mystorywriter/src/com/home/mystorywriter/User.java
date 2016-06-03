package com.home.mystorywriter;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Example user object that is persisted to disk by the DAO and other example classes.
 */
@XmlRootElement(name="user")
@DatabaseTable(tableName = "Users")
public class User implements Serializable {

// for QueryBuilder to be able to find the fields
	public static final String NAME_FIELD_NAME = "username";
	public static final String PASSWORD_FIELD_NAME = "password";
	public static final String PROFILE_ID = "profileid";


	//@DatabaseField(generatedId = true)
	//private int rowid;

	@DatabaseField(columnName = NAME_FIELD_NAME, canBeNull = false)
	private String username;

	@DatabaseField(columnName = PASSWORD_FIELD_NAME)
	private String password;
	
	@DatabaseField(columnName = PROFILE_ID)
	private String profileid;

	@DatabaseField(generatedId = true)
	private Integer id;
	
	
	User() {
		// all persisted classes must define a no-arg constructor with at least package visibility
	}

	public User(Integer id) {
		this.id = id;
	}

	public User(Integer id,String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}
    
	public User(String username, String password) {
		
		this.username = username;
		this.password = password;
	}
	@XmlAttribute
	public Integer getId() {
		return id;
	}
	@XmlElement
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	@XmlElement
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return username.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == null || other.getClass() != getClass()) {
			return false;
		}
		return username.equals(((User) other).username);
	}
}