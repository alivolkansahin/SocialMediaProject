package com.socialmedia.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthActivateRequestDto {

//    @NotBlank(message = "Id must not be blank!")
    private Long id;

    @NotBlank(message = "Activation Code must not be blank!")
    private String activationCode;

}
