package com.socialmedia.repository;

import com.socialmedia.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends JpaRepository<Auth,Long> {

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

}
