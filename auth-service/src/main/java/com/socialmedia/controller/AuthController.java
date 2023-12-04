package com.socialmedia.controller;

import com.socialmedia.dto.request.AuthRegisterRequestDto;
import com.socialmedia.entity.Auth;
import com.socialmedia.exception.AuthServiceException;
import com.socialmedia.exception.ErrorType;
import com.socialmedia.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.socialmedia.constant.EndPoint.*;

@RestController
@RequestMapping(ROOT + AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping(REGISTER)
    public ResponseEntity<Auth> register(AuthRegisterRequestDto dto){
        if(!dto.getPassword().equals(dto.getRePassword()))
            throw new AuthServiceException(ErrorType.PASSWORDS_NOT_MATCH);
        return ResponseEntity.ok(authService.register(dto));
    }


}
