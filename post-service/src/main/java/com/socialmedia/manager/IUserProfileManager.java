package com.socialmedia.manager;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "post-userprofile", url = "http://localhost:9091/api/v1/user", decode404 = true)
public interface IUserProfileManager {

    @GetMapping("/getuseridfromtoken")
    Long getUserIdByToken(@RequestParam String token);

}
