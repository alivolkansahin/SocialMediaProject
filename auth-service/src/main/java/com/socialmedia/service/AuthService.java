package com.socialmedia.service;

import com.socialmedia.dto.request.*;
import com.socialmedia.dto.response.AuthRegisterResponseDto;
import com.socialmedia.entity.Auth;
import com.socialmedia.entity.enums.EStatus;
import com.socialmedia.exception.AuthManagerException;
import com.socialmedia.exception.ErrorType;
import com.socialmedia.manager.IUserProfileManager;
import com.socialmedia.mapper.IAuthMapper;
import com.socialmedia.rabbitmq.producer.ActivationProducer;
import com.socialmedia.rabbitmq.producer.MailProducer;
import com.socialmedia.rabbitmq.producer.RegisterProducer;
import com.socialmedia.repository.AuthRepository;
import com.socialmedia.utility.JWTTokenManager;
import com.socialmedia.utility.ServiceManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService extends ServiceManager<Auth, Long> {

    private final AuthRepository authRepository;

    private final JWTTokenManager jwtTokenManager;

    private final IUserProfileManager iUserProfileManager;

    private final RegisterProducer registerProducer;

    private final ActivationProducer activationProducer;
    private final MailProducer mailProducer;

    private final CacheManager cacheManager;

    public AuthService(AuthRepository authRepository, JWTTokenManager jwtTokenManager, IUserProfileManager iUserProfileManager, RegisterProducer registerProducer, ActivationProducer activationProducer, MailProducer mailProducer, CacheManager cacheManager) {
        super(authRepository);
        this.authRepository = authRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.iUserProfileManager = iUserProfileManager;
        this.registerProducer = registerProducer;
        this.activationProducer = activationProducer;
        this.mailProducer = mailProducer;
        this.cacheManager = cacheManager;
    }

//    @Transactional    // RabbitMQ ile buna gerek kalmadı, rabbitmq ile zaten asenkron haberleşmelerini sağladığımız için, diğer tarafın açık kapalı olması farketmez benim için.
    public AuthRegisterResponseDto register(AuthRegisterRequestDto dto) {
        if(existsByEmail(dto.getEmail())) throw new AuthManagerException(ErrorType.EMAIL_ALREADY_EXISTS);
        if(existsByUsername(dto.getUsername())) throw new AuthManagerException(ErrorType.USERNAME_ALREADY_EXISTS);
        Auth auth = IAuthMapper.INSTANCE.registerDtoToAuth(dto);
        save(auth);
        String token = "Bearer " + jwtTokenManager.createToken(auth.getId(), auth.getRole()).get();
        iUserProfileManager.createNewUser(IAuthMapper.INSTANCE.registerAuthToUserDto(auth), token);
        mailProducer.sendActivationMail(IAuthMapper.INSTANCE.mailAuthToModel(auth));
        return IAuthMapper.INSTANCE.registerAuthToDto(auth);
    }

    public AuthRegisterResponseDto registerRabbit(AuthRegisterRequestDto dto) {
        if(existsByEmail(dto.getEmail())) throw new AuthManagerException(ErrorType.EMAIL_ALREADY_EXISTS);
        if(existsByUsername(dto.getUsername())) throw new AuthManagerException(ErrorType.USERNAME_ALREADY_EXISTS);
        Auth auth = IAuthMapper.INSTANCE.registerDtoToAuth(dto);
        save(auth);
        registerProducer.sendNewUser(IAuthMapper.INSTANCE.registerAuthToModel(auth));
        mailProducer.sendActivationMail(IAuthMapper.INSTANCE.mailAuthToModel(auth));
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

    @CacheEvict(cacheNames = "findbystatus", allEntries = true)
    public String activation(AuthActivateRequestDto dto) {
        Optional<Auth> optionalAuth = authRepository.findById(dto.getId());
        if(optionalAuth.isEmpty()) throw new AuthManagerException(ErrorType.USER_NOT_FOUND_LOGIN);  // buraları düzenle
        if(!optionalAuth.get().getActivationCode().equals(dto.getActivationCode())) throw new AuthManagerException(ErrorType.ACTIVATION_CODE_INVALID);
//        if(optionalAuth.get().getStatus().equals(EStatus.ACTIVE)) throw new AuthServiceException(ErrorType.USER_ALREADY_ACTIVE);
//        if(optionalAuth.get().getStatus().equals(EStatus.BANNED)) throw new AuthServiceException(ErrorType.USER_BANNED);
//        optionalAuth.get().setStatus(EStatus.ACTIVE);
//        save(optionalAuth.get());
//        return "Activation Success!";
        //anatasyon olmasaydı : cacheManager.getCache("findbystatus").evict(optionalAuth.get().getStatus());
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
//                iUserProfileManager.activation(auth.getId());
                activationProducer.convertAndSendToRabbit(auth.getId());
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

    public String updateAuth(AuthUpdateRequestDto dto) {
        Optional<Auth> optionalAuth = findById(dto.getId());
        Auth auth = optionalAuth.orElseThrow(() -> new AuthManagerException(ErrorType.USER_NOT_FOUND_BY_ID));
        if (existsByEmail(dto.getEmail())) throw new AuthManagerException(ErrorType.EMAIL_ALREADY_EXISTS);
        auth.setEmail(dto.getEmail());
        update(auth);
        return "Update successful!";
    }

    public String changeStatusToDeleted(AuthDeleteRequestDto dto) {
        Optional<Auth> optionalAuth = findById(dto.getId());
        Auth auth = optionalAuth.orElseThrow(() -> new AuthManagerException(ErrorType.USER_NOT_FOUND_BY_ID));
        auth.setStatus(EStatus.DELETED);
        update(auth);
        return "Delete successful!";
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
