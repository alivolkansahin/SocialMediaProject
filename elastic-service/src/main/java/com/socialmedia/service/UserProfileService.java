package com.socialmedia.service;


import com.socialmedia.entity.UserProfile;
import com.socialmedia.repository.IUserProfileRepository;
import com.socialmedia.utility.ServiceManager;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService extends ServiceManager<UserProfile, String> {

    private final IUserProfileRepository userProfileRepository;

    public UserProfileService(IUserProfileRepository userProfileRepository) {
        super(userProfileRepository);
        this.userProfileRepository = userProfileRepository;
    }


}
