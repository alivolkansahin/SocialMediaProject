package com.socialmedia.repository;

import com.socialmedia.entity.UserProfile;
import com.socialmedia.entity.enums.EStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends MongoRepository<UserProfile, String> {

    Optional<UserProfile> findByAuthId(Long authid);

    Optional<UserProfile> findByUsername(String username);

    List<UserProfile> findByStatus(EStatus status);
}
