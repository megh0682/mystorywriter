package com.home.mystorywriter;

import java.io.Serializable;

public class LoginBean
implements Serializable {
    private String userName;
    private String password;

    public LoginBean(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public LoginBean() {
    }

    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
