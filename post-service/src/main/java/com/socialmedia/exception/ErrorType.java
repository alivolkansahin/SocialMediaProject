package com.socialmedia.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorType { //errorlerimi tanımlayım, benim kullanacağım
    NO_USERS_EXIST(5001, "No users exist in database!", HttpStatus.NOT_FOUND), // 404
    USER_NOT_FOUND_BY_ID(5002, "No user found by this id!", HttpStatus.NOT_FOUND), // 404
    USERNAME_ALREADY_EXISTS(5003, "Username already exists!", HttpStatus.CONFLICT), // 409
    EMAIL_ALREADY_EXISTS(5004, "There is already an user registered with this email!", HttpStatus.CONFLICT),  // 409
    PASSWORD_MISMATCH(5005, "Passwords do not match!", HttpStatus.CONFLICT), // 409
    USER_ALREADY_DELETED(5006, "User already deleted!", HttpStatus.BAD_REQUEST), // 400
    USER_NOT_FOUND_LOGIN(5007,"Email and/or password missing or incorrect!", HttpStatus.NOT_FOUND),
    USER_NOT_ACTIVE(5008, "User status is not active!",  HttpStatus.FORBIDDEN), // 403
    BLANK_PARAMETER_ENTRY(5401, "Blank parameter entry!", HttpStatus.BAD_REQUEST), // 400
    INTERNAL_ERROR_SERVER(6000, "Server Error!", HttpStatus.INTERNAL_SERVER_ERROR),
    PARAMETER_NOT_VALID(5000,"Parameter Error", HttpStatus.BAD_REQUEST),
    ACTIVATION_CODE_INVALID(5009, "Activation code invalid!" ,HttpStatus.BAD_REQUEST),
    USER_ALREADY_ACTIVE(5010, "Activation already completed!", HttpStatus.BAD_REQUEST),
    USER_BANNED(5011, "This user is banned!", HttpStatus.BAD_REQUEST),
    INVALID_TOKEN(6000, "Invalid token!", HttpStatus.BAD_REQUEST),
    TOKEN_NOT_CREATED(6001,"Token not created!", HttpStatus.BAD_REQUEST);
/*
    NO_POSTS_EXIST(5101, "No posts exist in database!", HttpStatus.NOT_FOUND), // 404
    POST_NOT_FOUND_BY_ID(5102, "No post found by this id!", HttpStatus.NOT_FOUND), // 404
    POST_NOT_FOUND_BY_USER(5103, "No posts published by this user!", HttpStatus.NOT_FOUND), // 404
    POST_NOT_FOUND_BY_CATEGORY(5104, "No posts published in this category!", HttpStatus.NOT_FOUND), // 404
    POST_NOT_FOUND_BY_PARAMETER(5105, "No posts found by this search parameter!", HttpStatus.NOT_FOUND), // 404
    POST_ALREADY_DELETED(5106, "Post already deleted!", HttpStatus.BAD_REQUEST), // 400

    NO_CATEGORIES_EXIST(5201, "No categories exist in database!", HttpStatus.NOT_FOUND), // 404
    CATEGORY_NOT_FOUND_BY_ID(5202, "No category found by this id", HttpStatus.NOT_FOUND), // 404
    CATEGORY_NOT_FOUND_BY_NAME(5203, "No category found by this name!", HttpStatus.NOT_FOUND), // 404
    CATEGORY_NAME_ALREADY_EXISTS(5204, "Category with given name already exists therefore cannot be created again!", HttpStatus.CONFLICT), // 409
    CATEGORY_ALREADY_DELETED(5205, "Category already deleted!", HttpStatus.BAD_REQUEST), // 400

    NO_COMMENT_EXISTS(5301, "No comments found in database!", HttpStatus.NOT_FOUND), // 404
    COMMENT_NOT_FOUND_BY_ID(5302, "No comment found by this id!", HttpStatus.NOT_FOUND), // 404
    COMMENT_NOT_FOUND_BY_USER(5303,"No comments have been made by this user!", HttpStatus.NOT_FOUND), // 404
    COMMENT_NOT_FOUND_ON_POST(5304,"No comments have been made on this post!", HttpStatus.NOT_FOUND), // 404
    COMMENT_ALREADY_DELETED(5305,"Comment already deleted!",HttpStatus.BAD_REQUEST), // 400
*/

//    BAD_PARAMETERS_FOLLOW_REQUEST(5402, "Follower and Target Id's can't be same!", HttpStatus.BAD_REQUEST), // 400



    private int code;
    private String message;
    private HttpStatus httpStatus;
}
