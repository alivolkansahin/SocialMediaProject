package com.socialmedia.repository;

import com.socialmedia.entity.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthRepository extends JpaRepository<Auth,Long> {

    Boolean existsByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByUsernameAndPassword(String username, String password);
    Optional<Auth> findOptionalByEmailAndPassword(String email, String password);
    Optional<Auth> findOptionalByEmail(String email);

}
