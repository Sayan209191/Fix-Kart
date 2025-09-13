package com.fixkart.FixKart.util;

public class PasswordUtil {
    private PasswordUtil() {
    }
    public static boolean isValidFormat(String password){
        if(password == null) return false;
        // String regex = "^(?=.*[0-9])(?=.*[^a-zA-Z0-9]).{8,}$";
        // At least 1 digit, 1 lowercase, 1 uppercase, 1 special char, min length 8
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).{8,}$";
        return password.matches(regex);
    }
}
