package com.socialmedia.service;

import com.socialmedia.dto.request.*;
import com.socialmedia.dto.response.UserProfileResponseDto;
import com.socialmedia.entity.UserProfile;
import com.socialmedia.entity.enums.EStatus;
import com.socialmedia.exception.ErrorType;
import com.socialmedia.exception.UserManagerException;
import com.socialmedia.manager.IAuthManager;
import com.socialmedia.mapper.IUserMapper;
import com.socialmedia.rabbitmq.model.RegisterElasticModel;
import com.socialmedia.rabbitmq.model.RegisterModel;
import com.socialmedia.rabbitmq.producer.RegisterElasticProducer;
import com.socialmedia.repository.UserProfileRepository;
import com.socialmedia.utility.JWTTokenManager;
import com.socialmedia.utility.ServiceManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class UserProfileService extends ServiceManager<UserProfile, String> {

    private final UserProfileRepository userProfileRepository;

    private final JWTTokenManager jwtTokenManager;

    private final IAuthManager iAuthManager;

    private final RegisterElasticProducer registerElasticProducer;

    public UserProfileService(UserProfileRepository userProfileRepository, JWTTokenManager jwtTokenManager, IAuthManager iAuthManager, RegisterElasticProducer registerElasticProducer) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
        this.jwtTokenManager = jwtTokenManager;
        this.iAuthManager = iAuthManager;
        this.registerElasticProducer = registerElasticProducer;
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

//    public Long getUserIdByToken(String token) {
//        Optional<Long> idFromToken = jwtTokenManager.getIdFromToken(token);
//        if(idFromToken.isEmpty()) throw new UserManagerException(ErrorType.INVALID_TOKEN);
//        Optional<UserProfile> optionalUser = userProfileRepository.findByAuthId(idFromToken.get());
//        UserProfile userProfile = optionalUser.orElseThrow(() -> new UserManagerException(ErrorType.USER_NOT_FOUND_LOGIN));
//        return userProfile.getId();
//    }

    public void createNewUserWithRabbit(RegisterModel registerModel) {
        UserProfile userProfile = IUserMapper.INSTANCE.saveModelToUser(registerModel);
        save(userProfile);
        registerElasticProducer.sendNewUser(IUserMapper.INSTANCE.UserProfileToModel(userProfile));
    }

    @Cacheable(value = "findbyusernameredis" , key = "#username.toLowerCase()") //cache'e küçük username yazdırıyoruz
    public UserProfile findByUsername(String username) {
//        try {   // DAHA İYİ GÖREBİLMEK İÇİN EKLEYEBİLİRSİN
//            Thread.sleep(1000L);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        return userProfileRepository.findByUsername(username.toLowerCase()).orElseThrow(() -> new UserManagerException(ErrorType.USER_NOT_FOUND_LOGIN));
    }

    @Cacheable(value = "findbystatus")
    public List<UserProfile> findByStatus(EStatus status) {
        List<UserProfile> userList = userProfileRepository.findByStatus(status);
//        if (userList.isEmpty()) throw new UserManagerException(ErrorType.USER_NOT_FOUND_BY_STATUS);  // boşsa hata fırlatmayalım diye söyledi hoca...
        return userList;
    }

    public List<UserProfileResponseDto> findAllForElasticService(){
        return userProfileRepository.findAll().stream().map(userProfile -> IUserMapper.INSTANCE.toUserProfileResponseDto(userProfile)).toList();
    }

    // bu aşağı indikçe otomatik yeni elemanların gelmesi (ürün ararken mesela) mevzusunu methodlaştırmaya çalışıyoruz.
    // ASC DESC
    public Page<UserProfile> findAllByPageable(int pageSize, int pageNumber, String direction, String sortParameter) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortParameter); // pageSize: kaç eleman, pageNumber: Kaçıncı sayfa, direction: ASC or DESC, sortParameter: idye göre mi username e göre mi
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return userProfileRepository.findAll(pageable);
    }

    public Slice<UserProfile> findAllBySlice(int pageSize, int pageNumber, String direction, String sortParameter) { // sadece geri dönüş tipi değişti
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortParameter); // pageSize: kaç eleman, pageNumber: Kaçıncı sayfa, direction: ASC or DESC, sortParameter: idye göre mi username e göre mi
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return userProfileRepository.findAll(pageable);
    }
}
