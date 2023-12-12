package com.socialmedia.service;

import com.socialmedia.dto.request.*;
import com.socialmedia.entity.UserProfile;
import com.socialmedia.entity.enums.EStatus;
import com.socialmedia.exception.ErrorType;
import com.socialmedia.exception.UserManagerException;
import com.socialmedia.manager.IAuthManager;
import com.socialmedia.mapper.IUserMapper;
import com.socialmedia.rabbitmq.model.RegisterModel;
import com.socialmedia.repository.UserProfileRepository;
import com.socialmedia.utility.JWTTokenManager;
import com.socialmedia.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile, Long> {

    private final UserProfileRepository userProfileRepository;

    private final JWTTokenManager jwtTokenManager;

    private final IAuthManager iAuthManager;

    public UserProfileService(UserProfileRepository userProfileRepository, JWTTokenManager jwtTokenManager, IAuthManager iAuthManager) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.iAuthManager = iAuthManager;
    }

    public Boolean createNewUser(UserSaveRequestDto dto) {
        UserProfile userProfile = IUserMapper.INSTANCE.saveDtoToUserProfile(dto);
        save(userProfile);
        return true;
    }

    public String activation(Long authid) {
        Optional<UserProfile> optionalUser = userProfileRepository.findByAuthId(authid);
        if(optionalUser.isEmpty()) throw new UserManagerException(ErrorType.USER_NOT_FOUND_LOGIN);  // buraları düzenle
//        if(!optionalUser.get().getActivationCode().equals(dto.getActivationCode())) throw new UserManagerException(ErrorType.ACTIVATION_CODE_INVALID);
        if(optionalUser.get().getStatus().equals(EStatus.ACTIVE)) throw new UserManagerException(ErrorType.USER_ALREADY_ACTIVE);
        if(optionalUser.get().getStatus().equals(EStatus.BANNED)) throw new UserManagerException(ErrorType.USER_BANNED);
        optionalUser.get().setStatus(EStatus.ACTIVE);
        update(optionalUser.get());
        return "Activation complete!";
    }

    public String updateProfile(UserUpdateRequestDto dto) {
        Optional<Long> idFromToken = jwtTokenManager.getIdFromToken(dto.getToken());
        if(idFromToken.isEmpty()) throw new UserManagerException(ErrorType.INVALID_TOKEN);


        Optional<UserProfile> optionalUser = userProfileRepository.findByAuthId(idFromToken.get());
//        if(optionalUser.isEmpty()) throw new UserManagerException(ErrorType.USER_NOT_FOUND_LOGIN);  // buraları düzenle
        UserProfile userProfile = optionalUser.orElseThrow(() -> new UserManagerException(ErrorType.USER_NOT_FOUND_LOGIN));

        if(!userProfile.getEmail().equals(dto.getEmail())){
            userProfile.setEmail(dto.getEmail() == null ? userProfile.getEmail() : dto.getEmail());
            iAuthManager.updateProfile(IUserMapper.INSTANCE.updateUserToAuthDto(userProfile));
        }
        userProfile.setAvatar(dto.getAvatar() == null ? userProfile.getAvatar() : dto.getAvatar());
        userProfile.setPhone(dto.getPhone() == null ? userProfile.getPhone() : dto.getPhone());
        userProfile.setAddress(dto.getAddress() == null ? userProfile.getAddress() : dto.getAddress());
        userProfile.setAbout(dto.getAbout() == null ? userProfile.getAbout() : dto.getAbout());

        update(userProfile);
        return "Update successful!";
    }

    public String deleteProfile(UserDeleteRequestDto dto) {
        Optional<Long> idFromToken = jwtTokenManager.getIdFromToken(dto.getToken());
        if(idFromToken.isEmpty()) throw new UserManagerException(ErrorType.INVALID_TOKEN);
        Optional<UserProfile> optionalUser = userProfileRepository.findByAuthId(idFromToken.get());
        UserProfile userProfile = optionalUser.orElseThrow(() -> new UserManagerException(ErrorType.USER_NOT_FOUND_LOGIN));
        userProfile.setStatus(EStatus.DELETED);
        update(userProfile);
        iAuthManager.changeStatusToDeleted(IUserMapper.INSTANCE.deleteUserToAuthDto(userProfile));
        return "Delete successful!";
    }

    public Long getUserIdByToken(String token) {
        Optional<Long> idFromToken = jwtTokenManager.getIdFromToken(token);
        if(idFromToken.isEmpty()) throw new UserManagerException(ErrorType.INVALID_TOKEN);
        Optional<UserProfile> optionalUser = userProfileRepository.findByAuthId(idFromToken.get());
        UserProfile userProfile = optionalUser.orElseThrow(() -> new UserManagerException(ErrorType.USER_NOT_FOUND_LOGIN));
        return userProfile.getId();
    }

    public void createNewUserWithRabbit(RegisterModel registerModel) {
        UserProfile userProfile = IUserMapper.INSTANCE.saveModelToUser(registerModel);
        save(userProfile);
    }

}
