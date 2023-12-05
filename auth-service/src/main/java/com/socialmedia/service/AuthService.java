package com.socialmedia.service;

import com.socialmedia.dto.request.AuthActivateRequestDto;
import com.socialmedia.dto.request.AuthLoginRequestDto;
import com.socialmedia.dto.request.AuthRegisterRequestDto;
import com.socialmedia.dto.request.UserSaveRequestDto;
import com.socialmedia.dto.response.AuthRegisterResponseDto;
import com.socialmedia.entity.Auth;
import com.socialmedia.entity.enums.EStatus;
import com.socialmedia.exception.AuthManagerException;
import com.socialmedia.exception.ErrorType;
import com.socialmedia.manager.IUserProfileManager;
import com.socialmedia.mapper.IAuthMapper;
import com.socialmedia.repository.AuthRepository;
import com.socialmedia.utility.JWTTokenManager;
import com.socialmedia.utility.ServiceManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth, Long> {

    private final AuthRepository authRepository;

    private final JWTTokenManager jwtTokenManager;

    private final IUserProfileManager iUserProfileManager;

    public AuthService(AuthRepository authRepository, JWTTokenManager jwtTokenManager, IUserProfileManager iUserProfileManager) {
        super(authRepository);
        this.authRepository = authRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.iUserProfileManager = iUserProfileManager;
    }

    @Transactional
    public AuthRegisterResponseDto register(AuthRegisterRequestDto dto) {
        if(existsByEmail(dto.getEmail())) throw new AuthManagerException(ErrorType.EMAIL_ALREADY_EXISTS);
        if(existsByUsername(dto.getUsername())) throw new AuthManagerException(ErrorType.USERNAME_ALREADY_EXISTS);
        Auth auth = IAuthMapper.INSTANCE.registerDtoToAuth(dto);
        save(auth);
        iUserProfileManager.createNewUser(IAuthMapper.INSTANCE.registerAuthToUserDto(auth));
        return IAuthMapper.INSTANCE.registerAuthToDto(auth);
    }

    private Boolean existsByUsername(String username) {
        return authRepository.existsByUsername(username);
    }

    private Boolean existsByEmail(String email) {
        return authRepository.existsByEmail(email);
    }

    public String login(AuthLoginRequestDto dto) {
        Optional<Auth> optionalAuth = authRepository.findOptionalByEmailAndPassword(dto.getEmail(), dto.getPassword());
        if(optionalAuth.isEmpty()) throw new AuthManagerException(ErrorType.USER_NOT_FOUND_LOGIN); // buraları düzenle
        if(!optionalAuth.get().getStatus().equals(EStatus.ACTIVE)) throw new AuthManagerException(ErrorType.USER_NOT_ACTIVE);
        Optional<String> token = jwtTokenManager.createToken(optionalAuth.get().getId(), optionalAuth.get().getRole());
        if(token.isEmpty()) throw new AuthManagerException(ErrorType.TOKEN_NOT_CREATED);
//        return IAuthMapper.INSTANCE.loginAuthToDto(optionalAuth.get());
        return token.get();
    }

    public String activation(AuthActivateRequestDto dto) {
        Optional<Auth> optionalAuth = authRepository.findById(dto.getId());
        if(optionalAuth.isEmpty()) throw new AuthManagerException(ErrorType.USER_NOT_FOUND_LOGIN);  // buraları düzenle
        if(!optionalAuth.get().getActivationCode().equals(dto.getActivationCode())) throw new AuthManagerException(ErrorType.ACTIVATION_CODE_INVALID);
//        if(optionalAuth.get().getStatus().equals(EStatus.ACTIVE)) throw new AuthServiceException(ErrorType.USER_ALREADY_ACTIVE);
//        if(optionalAuth.get().getStatus().equals(EStatus.BANNED)) throw new AuthServiceException(ErrorType.USER_BANNED);
//        optionalAuth.get().setStatus(EStatus.ACTIVE);
//        save(optionalAuth.get());
//        return "Activation Success!";
        return statusControl(optionalAuth.get());
    }

    public String statusControl(Auth auth){
        switch (auth.getStatus()){
            case ACTIVE -> {
                return "Hesap Zaten aktif";
            }
            case PENDING -> {
                auth.setStatus(EStatus.ACTIVE);
                update(auth);
                return "Activation Success!";
            }
            case BANNED -> {
                return "Hesabınız Engellenmiştir";
            }
            case DELETED -> {
                return "Hesabınız Silinmiştir";
            }
            default -> {
                throw new AuthManagerException(ErrorType.INTERNAL_ERROR_SERVER);
            }
        }
    }

    public String softDelete(Long id) {
        Optional<Auth> optionalAuth = findById(id);
        if(optionalAuth.isEmpty()){
            throw new AuthManagerException(ErrorType.USER_NOT_FOUND_BY_ID);
        }
        if(!optionalAuth.get().getStatus().equals(EStatus.DELETED)){
            optionalAuth.get().setStatus(EStatus.DELETED);
            save(optionalAuth.get());
            return id + " idli Kullanıcı Silindi";
        }else {
            throw new AuthManagerException(ErrorType.USER_ALREADY_DELETED);
        }
    }

//    public String activeStatus(AuthActivateRequestDto dto) {
//        Optional<Auth> optionalAuth = authRepository.findByIdAndActivationCode(dto.getId(), dto.getActivationCode());
//        if (optionalAuth.isPresent()){
//            if (optionalAuth.get().getStatus().equals(EStatus.PENDING)){
//                optionalAuth.get().setStatus(EStatus.ACTIVE);
//                authRepository.save(optionalAuth.get());
//                return "Aktivasyon başarılı";
//            }
//            throw new AuthManagerException(ErrorType.STATUS_NOT_SUITABLE, ErrorType.STATUS_NOT_SUITABLE.getMessage() + optionalAuth.get().getStatus().name());
//        }
//        throw new AuthManagerException(ErrorType.USER_NOT_FOUND_LOGIN);
//    }
}
