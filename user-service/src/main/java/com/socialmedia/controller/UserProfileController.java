package com.socialmedia.controller;

import com.socialmedia.dto.request.UserDeleteRequestDto;
import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.dto.request.UserUpdateRequestDto;
import com.socialmedia.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping("/activation/{authid}")
    public ResponseEntity<String> activation(@PathVariable Long authid){
        return ResponseEntity.ok(userProfileService.activation(authid));
    }

    @PutMapping(UPDATE)
    public ResponseEntity<String> updateProfile(@RequestBody @Valid UserUpdateRequestDto dto){
        return ResponseEntity.ok(userProfileService.updateProfile(dto));
    }

    @DeleteMapping(DELETE)
    public ResponseEntity<String> deleteProfile(@RequestBody UserDeleteRequestDto dto){
        return ResponseEntity.ok(userProfileService.deleteProfile(dto));
    }

}
