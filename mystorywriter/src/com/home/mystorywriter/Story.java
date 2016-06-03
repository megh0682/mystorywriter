package com.home.mystorywriter;
import java.io.Serializable;
import java.sql.Blob;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Example user object that is persisted to disk by the DAO and other example classes.
 */
@XmlRootElement(name = "story")
@DatabaseTable(tableName = "Stories")
public class Story implements Serializable {

// for QueryBuilder to be able to find the fields
	public static final String TITLE_FIELD_NAME = "title";
	public static final String BEGIN_FIELD_NAME = "firstPart";
	public static final String MIDDLE_FIELD_NAME = "middlePart";
	public static final String END_FIELD_NAME = "lastPart";
	public static final String PICTURE_FIELD_NAME = "storyPic";
	public static final String AUTHORID_FIELD_NAME = "authorid";

	

	@DatabaseField(columnName = TITLE_FIELD_NAME, canBeNull = false)
	private String title;
	
	@DatabaseField(columnName = BEGIN_FIELD_NAME, canBeNull = false)
	private String firstPart;
	
	@DatabaseField(columnName =MIDDLE_FIELD_NAME, canBeNull = false)
	private String middlePart;
	
	@DatabaseField(columnName = END_FIELD_NAME, canBeNull = false)
	private String lastPart;

	@DatabaseField(columnName = PICTURE_FIELD_NAME, dataType = DataType.BYTE_ARRAY)
	private byte[]  storypic;
	
	@DatabaseField(columnName =AUTHORID_FIELD_NAME)
	private Integer authorid;

	@DatabaseField(generatedId = true)
	private Integer id;
	
	
	Story() {
		// all persisted classes must define a no-arg constructor with at least package visibility
	}
	
	public Story(Integer id) {
	        this.id = id;
	}   	

	public Story(Integer id, String title, String firstPart, String middlePart, String lastPart, Integer authorid) {
        this.id = id;
        this.title = title;
        this.firstPart = firstPart;
        this.middlePart = middlePart;
        this.lastPart = lastPart;
        this.authorid=authorid;
        
    }
	
	public Story(Integer id, String title, String firstPart, String middlePart, String lastPart,byte[]storypic, Integer authorid) {
        this.id = id;
        this.title = title;
        this.firstPart = firstPart;
        this.middlePart = middlePart;
        this.lastPart = lastPart;
        this.storypic = storypic;
        this.authorid=authorid;
    }

	 @XmlAttribute(required=true)
	public Integer getId() {
		return id;
	}
	 @XmlElement 
	public String getTitle() {
		return title;
	}
	 @XmlElement
	public String getFirstPart() {
		return firstPart;
	}
	 @XmlElement
	public String getMiddlePart() {
		return middlePart;
	}
	 @XmlElement
	public String getLastPart() {
		return lastPart;
	}
 	
	public byte[] getStorypic() {
		return storypic;
	}
	@XmlElement(required=true)
	public Integer getAuthorid() {
		return authorid;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setFirstPart(String firstPart) {
		this.firstPart = firstPart;
	}
	
	public void setMiddlePart(String middlePart) {
		this.middlePart = middlePart;
	}
	
	public void setLastPart(String lastPart) {
		this.lastPart = lastPart;
	}
		
	public void setStorypic(byte[] storypic) {
		this.storypic = storypic;
	}

	public void setAuthorid(Integer authorid) {
		this.authorid = authorid;
	}
	

	 @Override
	    public int hashCode() {
	        int hash = 0;
	        hash += (id != null ? id.hashCode() : 0);
	        return hash;
	    }

	 @Override
	    public boolean equals(Object object) {
	        // TODO: Warning - this method won't work in the case the id fields are not set
	        if (!(object instanceof Profile)) {
	            return false;
	        }
	        Story other = (Story) object;
	        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
	            return false;
	        }
	        return true;
	    }

	    @Override
	    public String toString() {
	        return "com.home.mybuddywriter.Profile[ id=" + id + " ]";
	    }
}