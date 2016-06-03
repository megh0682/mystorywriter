package com.home.mystorywriter;

import com.home.mystorywriter.LoginBean;

public class LoginValidator {
    public static boolean validate(LoginBean bean) {
        if (!bean.getUserName().matches("^\\w{1,12}$")) {
            return false;
        }
        if (!bean.getPassword().matches("^[^'\"&<>]{6,15}$")) {
            return false;
        }
        return true;
    }
}