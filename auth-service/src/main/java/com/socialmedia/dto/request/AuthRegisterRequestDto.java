package com.socialmedia.dto.request;

import com.socialmedia.constant.ErrorStaticMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthRegisterRequestDto {

    @NotBlank(message = "Username must not be blank!")
    @Size(min = 2, max = 20, message = ErrorStaticMessage.USERNAME_NOT_VALID)
    private String username;

    @NotBlank(message = "Email must not be blank!")
    @Email(message = ErrorStaticMessage.EMAIL_NOT_VALID)
    private String email;

    @NotBlank(message = "Password must not be blank!")
    @Size(min = 2, max = 20, message = ErrorStaticMessage.PASSWORD_NOT_VALID)
    private String password;

    @Size(min = 2, max = 20, message = ErrorStaticMessage.PASSWORD_NOT_VALID)
    private String rePassword;

}
