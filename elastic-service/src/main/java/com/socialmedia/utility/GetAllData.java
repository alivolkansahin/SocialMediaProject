package com.socialmedia.utility;

import com.socialmedia.dto.response.UserProfileResponseDto;
import com.socialmedia.entity.UserProfile;
import com.socialmedia.manager.IUserManager;
import com.socialmedia.mapper.IUserMapper;
import com.socialmedia.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllData {

    private final IUserManager userManager;
    private final UserProfileService userProfileService;

    @PostConstruct // The PostConstruct annotation is used on a method that needs to be executed after dependency injection is done to perform any initialization. This method must be invoked before the class is put into service
    public void initData(){
        List<UserProfileResponseDto> userList = userManager.findAllForElasticService().getBody();
        userProfileService.saveAll(IUserMapper.INSTANCE.dtoToUserProfile(userList));
    }

}
