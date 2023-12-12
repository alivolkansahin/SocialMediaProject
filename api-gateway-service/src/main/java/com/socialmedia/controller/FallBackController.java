package com.socialmedia.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallBackController {
    @GetMapping("/authservice")
    public ResponseEntity<String> authServiceFallBack(){
        return ResponseEntity.ok("Auth Service Şu anda Hizmet Vermemektedir!");
    }

    @GetMapping("/userservice")
    public ResponseEntity<String> userServiceFallBack(){
        return ResponseEntity.ok("User Service Şu anda Hizmet Vermemektedir!");
    }

    @GetMapping("/postservice")
    public ResponseEntity<String> postServiceFallBack(){
        return ResponseEntity.ok("Post Service Şu anda Hizmet Vermemektedir!");
    }
}
