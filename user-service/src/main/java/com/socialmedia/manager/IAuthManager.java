package com.socialmedia.manager;

import com.socialmedia.dto.request.AuthUpdateRequestDto;
import com.socialmedia.dto.request.UserSaveRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

import static com.socialmedia.constant.EndPoint.SAVE;
import static com.socialmedia.constant.EndPoint.UPDATE;

@FeignClient(name = "userprofile-auth", url = "http://localhost:9090/api/v1/auth", decode404 = true)
public interface IAuthManager {

    @PutMapping(UPDATE)
    ResponseEntity<String> updateProfile(@RequestBody @Valid AuthUpdateRequestDto dto);

}
