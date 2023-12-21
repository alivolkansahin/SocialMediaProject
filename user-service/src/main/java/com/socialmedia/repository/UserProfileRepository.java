package com.socialmedia.repository;

import com.socialmedia.entity.UserProfile;
import com.socialmedia.entity.enums.EStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends MongoRepository<UserProfile, String> {

    Optional<UserProfile> findByAuthId(Long authid);

    Optional<UserProfile> findByUsername(String username);

    List<UserProfile> findByStatus(EStatus status);

    @Query("{username : ?0}")
    Optional<UserProfile> findByUsernameQuery(String username);

    @Query("{status : 'ACTIVE'}")
    List<UserProfile> findAllByActiveUser();

    @Query("{authId : {$gte: ?0}}")
    List<UserProfile> findAllByAuthIdGreaterThan(Long authId);

    @Query("{$or :[{authId: {$gte: ?0}},{status: ?1}]}")
    List<UserProfile> findAllByAuthIdOrStatusGreaterThan(Long authId, String status);
       // bunun JPA hali şu olurdu (yani hibernate hali, postgres için)
//    List<UserProfile> findAllByAuthIdGreaterThanOrStatus(String authId, String status);




}
