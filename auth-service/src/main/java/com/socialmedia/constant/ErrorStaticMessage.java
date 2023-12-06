package com.socialmedia.constant;


public abstract class ErrorStaticMessage {
    public static final String BLANK_USERNAME = "Username must not be blank!";
    public static final String BLANK_PASSWORD = "Password must not be blank!";
    public static final String BLANK_EMAIL = "Email must not be blank!";
    public static final String USERNAME_LENGTH_NOT_VALID = "Username can only be between {min} and {max} characters long!";
    public static final String PASSWORD_NOT_VALID = "Password can only be between 8 and 32 characters long! 1 rakam, 1 Büyük karakter, 1 küçük karakter, 1 özel karakter barındırmalıdır.";
    //    public static final String PASSWORD_NOT_CONTAIN_DIGITS = "Password must contain at least one digit (0-9)!";
    public static final String EMAIL_NOT_VALID = "Check your email address for inappropriate characters!";
    public static final String USER_CONFIRMATION_REQUIRED = "Please confirm you are over 13 years old before continue!";
    public static final String USERNAME_CONTAINS_SPECIAL_CHAR = "Username must not contain any special characters!";

}
