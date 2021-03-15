package com.vito.webapp.backend.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtils {

    public static PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    public static String encodePassword(String pass){
        return getPasswordEncoder().encode(pass);
    }
}
