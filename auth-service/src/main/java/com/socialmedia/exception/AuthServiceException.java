package com.socialmedia.exception;

import lombok.Getter;

@Getter
public class AuthServiceException extends RuntimeException{

    private ErrorType errorType;  // bu sınıfı oluşturmak için errorType ver bana, enumda belirlediğim şeyler yani

    public AuthServiceException(ErrorType errorType){
        super(errorType.getMessage());
        this.errorType = errorType;
    }

    // ben bu errorType içindeki mesajı daha da customize etmek isteyebilirim(ezerim yani), onun içinde 2. bir constructor
    public AuthServiceException(ErrorType errorType, String customMessage){
        super(customMessage);
        this.errorType = errorType;
    }
}
