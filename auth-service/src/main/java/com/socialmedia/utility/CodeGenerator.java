package com.socialmedia.utility;

import java.util.Arrays;
import java.util.UUID;

public abstract class CodeGenerator {
    private static StringBuilder builder = new StringBuilder();
    public static String generateCode(){
        if(!builder.isEmpty()) builder.setLength(0);
        String randomCode = UUID.randomUUID().toString();
        Arrays.stream(randomCode.split("-")).forEach(code-> builder.append(code.charAt(0)));
        return builder.toString();
    }

}
