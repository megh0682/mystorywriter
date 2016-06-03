package com.home.mystorywriter;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "profile")
@DatabaseTable(tableName="Profiles")
public class Profile
implements Serializable {
    public static final String FIRSTNAME_FIELD_NAME = "firstname";
    public static final String LASTNAME_FIELD_NAME = "lastname";
    public static final String EMAIL_FIELD_NAME = "email";
    public static final String PROFILEPIC_FIELD_NAME = "profpic";
    public static final String USERID_FIELD_NAME = "userid";
    @DatabaseField(columnName=FIRSTNAME_FIELD_NAME, canBeNull=false)
    private String firstname;
    @DatabaseField(columnName=LASTNAME_FIELD_NAME, canBeNull=false)
    private String lastname;
    @DatabaseField(columnName=EMAIL_FIELD_NAME, canBeNull=false)
    private String email;
    @DatabaseField(columnName=PROFILEPIC_FIELD_NAME, dataType=DataType.BYTE_ARRAY)
    private byte[] profpic;
    @DatabaseField(columnName=USERID_FIELD_NAME)
    private Integer userid;
    @DatabaseField(generatedId = true)
    private Integer id;

    Profile() {
    }

    public Profile(Integer id) {
        this.id = id;
    }

    public Profile(Integer id, String firstname, String lastname, String email) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }
    
    public Profile(Integer id, String firstname, String lastname, String email,Integer userid) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.userid = userid;
    }
    
    public Profile( String firstname, String lastname, String email,Integer userid) {
        
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.userid = userid;
    }

    @XmlAttribute
    public Integer getId() {
        return this.id;
    }
    @XmlElement
    public String getFirstname() {
        return this.firstname;
    }
    @XmlElement
    public String getLastname() {
        return this.lastname;
    }
    @XmlElement
    public String getEmail() {
        return this.email;
    }
    
    public byte[] getProfpic() {
        return this.profpic;
    }
    @XmlElement
    public Integer getUserId() {
        return this.userid;
    }
   
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
   
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    public void setProfpic(byte[] profpic) {
        this.profpic = profpic;
    }
    
    public void setUserId(Integer userid) {
        this.userid = userid;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public int hashCode() {
        int hash = 0;
        return hash += this.id != null ? this.id.hashCode() : 0;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Profile)) {
            return false;
        }
        Profile other = (Profile)object;
        if (this.id == null && other.id != null || this.id != null && !this.id.equals(other.id)) {
            return false;
        }
        return true;
    }

    public String toString() {
        return "com.home.mybuddywriter.Profile[ id=" + this.id + " ]";
    }
}