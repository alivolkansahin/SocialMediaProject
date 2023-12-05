package com.socialmedia.manager;

import com.socialmedia.dto.request.UserSaveRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.socialmedia.constant.EndPoint.SAVE;

@FeignClient(name = "user-profile-manager", url = "http://localhost:9091/api/v1/user", decode404 = true)
public interface IUserProfileManager {

    @PostMapping(SAVE)
    ResponseEntity<Boolean> createNewUser(@RequestBody UserSaveRequestDto dto);

}
