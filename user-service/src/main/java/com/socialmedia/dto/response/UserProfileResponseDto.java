package com.socialmedia.dto.response;

import com.socialmedia.entity.enums.EStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserProfileResponseDto {

    private Long userProfileId;

    private Long authId;

    private String username;

    private String email;

    private String phone;

    private String avatar;

    private String address;

    private String about;

    private EStatus status;

}
