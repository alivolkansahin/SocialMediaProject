package com.socialmedia.controller;

import com.socialmedia.dto.request.AuthActivateRequestDto;
import com.socialmedia.dto.request.AuthLoginRequestDto;
import com.socialmedia.dto.request.AuthRegisterRequestDto;
import com.socialmedia.dto.response.AuthRegisterResponseDto;
import com.socialmedia.entity.Auth;
import com.socialmedia.exception.AuthManagerException;
import com.socialmedia.exception.ErrorType;
import com.socialmedia.service.AuthService;
import com.socialmedia.utility.JWTTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.socialmedia.constant.EndPoint.*;

@RestController
@RequestMapping(ROOT + AUTH)
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final JWTTokenManager jwtTokenManager;

    @PostMapping(REGISTER)
    public ResponseEntity<AuthRegisterResponseDto> register(@RequestBody @Valid AuthRegisterRequestDto dto){
        if(!dto.getPassword().equals(dto.getRePassword()))
            throw new AuthManagerException(ErrorType.PASSWORD_MISMATCH);
        return ResponseEntity.ok(authService.register(dto));
    }

    @PostMapping(LOGIN)
    public ResponseEntity<String> login(@RequestBody @Valid AuthLoginRequestDto dto){
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/activation")
    public ResponseEntity<String> activation(@RequestBody @Valid AuthActivateRequestDto dto){
        return ResponseEntity.ok(authService.activation(dto));
    }

    @DeleteMapping("/delete{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long authId){
        return ResponseEntity.ok(authService.softDelete(authId));
    }

    @GetMapping("/gettoken")
    public ResponseEntity<String> getToken(Long id){
        return ResponseEntity.ok(jwtTokenManager.createToken(id).get());
    }

    @GetMapping("/getidfromtoken")
    public ResponseEntity<Long> getIdFromToken(String token){
        return ResponseEntity.ok(jwtTokenManager.getIdFromToken(token).get());
    }

    @GetMapping("/getrolefromtoken")
    public ResponseEntity<String> getRoleFromToken(String token){
        return ResponseEntity.ok(jwtTokenManager.getRoleFromToken(token).get());
    }


}
