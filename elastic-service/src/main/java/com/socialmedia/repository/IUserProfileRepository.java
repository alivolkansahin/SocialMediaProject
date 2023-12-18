package com.socialmedia.repository;

import com.socialmedia.entity.UserProfile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserProfileRepository extends ElasticsearchRepository<UserProfile,String> {

}
