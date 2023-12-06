package com.socialmedia.dto.request;

import com.socialmedia.constant.ErrorStaticMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.socialmedia.constant.ErrorStaticMessage.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthRegisterRequestDto {

    @NotBlank(message = BLANK_USERNAME)
    @Size(min = 2, max = 20, message = USERNAME_LENGTH_NOT_VALID)
    private String username;

    @NotBlank(message = BLANK_EMAIL)
    @Email(message = EMAIL_NOT_VALID)
    private String email;

    @NotBlank(message = BLANK_PASSWORD)
    @Size(min = 2, max = 20, message = PASSWORD_NOT_VALID)
    private String password;

    @Size(min = 2, max = 20, message = PASSWORD_NOT_VALID)
    private String rePassword;

}
