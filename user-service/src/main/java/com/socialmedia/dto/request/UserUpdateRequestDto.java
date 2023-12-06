package com.socialmedia.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static com.socialmedia.constant.ErrorStaticMessage.BLANK_EMAIL;
import static com.socialmedia.constant.ErrorStaticMessage.EMAIL_NOT_VALID;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserUpdateRequestDto {

    @NotBlank
    private String token;

    @NotBlank(message = BLANK_EMAIL)
    @Email(message = EMAIL_NOT_VALID)
    private String email;

    private String phone;

    private String avatar;

    private String address;

    private String about;

}
