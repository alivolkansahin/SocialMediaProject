package com.socialmedia.manager;

import com.socialmedia.dto.response.UserProfileResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static com.socialmedia.constant.EndPoint.FINDALL;

@FeignClient(name = "elastic-userprofile", url = "http://localhost:9091/api/v1/user", decode404 = true)
public interface IUserManager {
    @GetMapping(FINDALL + "/forelastic")
    ResponseEntity<List<UserProfileResponseDto>> findAllForElasticService();

}
