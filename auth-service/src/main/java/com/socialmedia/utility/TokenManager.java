package com.socialmedia.utility;

import org.springframework.stereotype.Component;

@Component
public class TokenManager {

    //  1. Token üretelim.
    public String createToken(Long id){
        return "authtoken:id";
    }

    // 2. Üretilen tokendan bilgi çıkarımı yap.
    public Long getIdFromToken(String token){
        return Long.valueOf(token.substring(token.indexOf(":")+1));
//        return Long.valueOf(token.split(":")[1]);
    }
}
