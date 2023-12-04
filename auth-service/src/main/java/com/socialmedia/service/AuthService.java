package com.socialmedia.service;

import com.socialmedia.dto.request.AuthRegisterRequestDto;
import com.socialmedia.entity.Auth;
import com.socialmedia.exception.AuthServiceException;
import com.socialmedia.exception.ErrorType;
import com.socialmedia.mapper.IAuthMapper;
import com.socialmedia.repository.AuthRepository;
import com.socialmedia.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class AuthService extends ServiceManager<Auth, Long> {
    private final AuthRepository authRepository;

    public AuthService(AuthRepository authRepository) {
        super(authRepository);
        this.authRepository = authRepository;
    }

    public Auth register(AuthRegisterRequestDto dto) {
        if(existsByEmail(dto.getEmail())) throw new AuthServiceException(ErrorType.EMAIL_ALREADY_EXISTS);
        if(existsByUsername(dto.getUsername())) throw new AuthServiceException(ErrorType.USERNAME_ALREADY_EXISTS);
        Auth auth = IAuthMapper.INSTANCE.registerDtoToAuth(dto);
        save(auth);
        return auth;
    }

    private Boolean existsByUsername(String username) {
        return authRepository.existsByUsername(username);
    }

    private Boolean existsByEmail(String email) {
        return authRepository.existsByEmail(email);
    }

}
