package com.socialmedia.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthRegisterResponseDto {

    private Long id;

    private String activationCode;

    private String username;

}
