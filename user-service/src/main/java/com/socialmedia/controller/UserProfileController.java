package com.socialmedia.controller;

import com.socialmedia.dto.request.UserDeleteRequestDto;
import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.dto.request.UserUpdateRequestDto;
import com.socialmedia.entity.UserProfile;
import com.socialmedia.entity.enums.EStatus;
import com.socialmedia.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static com.socialmedia.constant.EndPoint.*;

@RestController
@RequestMapping(ROOT + USER)
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;

    @PostMapping(SAVE)
    public ResponseEntity<Boolean> createNewUser(@RequestBody UserSaveRequestDto dto){
        return ResponseEntity.ok(userProfileService.createNewUser(dto));
    }

//    @PostMapping("/activation/{authid}")    // rabbitmq ile asenkron bağladık
//    public ResponseEntity<String> activation(@PathVariable Long authid){
//        return ResponseEntity.ok(userProfileService.activation(authid));
//    }

    @PutMapping(UPDATE)
    public ResponseEntity<String> updateProfile(@RequestBody @Valid UserUpdateRequestDto dto){
        return ResponseEntity.ok(userProfileService.updateProfile(dto));
    }

    @DeleteMapping(DELETE)
    public ResponseEntity<String> deleteProfile(@RequestBody UserDeleteRequestDto dto){
        return ResponseEntity.ok(userProfileService.deleteProfile(dto));
    }

    @GetMapping("/getuseridfromtoken")
    public Long getUserIdByToken(@RequestParam String token){
        return userProfileService.getUserIdByToken(token);
    }

    @GetMapping(FINDALL)
    public ResponseEntity<List<UserProfile>> findall(){ // requestparam daha çok filtreleme işllemleri için
        return ResponseEntity.ok(userProfileService.findAll());
    }


    @GetMapping(FINDBYNAME + "/{username}")
    public ResponseEntity<UserProfile> findByUsername(@PathVariable String username){     //findbyusername methodu yazalım username değerine göre bize userpofile dönsün cache atalım
        return ResponseEntity.ok(userProfileService.findByUsername(username));
    }

    @GetMapping("/findbystatus")
    public ResponseEntity<List<UserProfile>> findByStatus(@RequestParam EStatus status){     //findbystatus
        return ResponseEntity.ok(userProfileService.findByStatus(status));
    }

}
