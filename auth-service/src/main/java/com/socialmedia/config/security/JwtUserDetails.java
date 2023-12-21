package com.socialmedia.config.security;

import com.socialmedia.entity.Auth;
import com.socialmedia.exception.AuthManagerException;
import com.socialmedia.exception.ErrorType;
import com.socialmedia.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JwtUserDetails implements UserDetailsService {

    private final AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public UserDetails loadUserByAuthId(Long authId) throws UsernameNotFoundException {
        Auth auth = authService.findById(authId).orElseThrow(() -> new AuthManagerException(ErrorType.USER_NOT_FOUND_BY_ID));
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(auth.getRole().toString()));
        return User.builder()
                .username(auth.getUsername())
                .password(auth.getPassword()) // UYGULAMADA EKLEDİK, teoride securityi userservicede eklemiştik burda authservicede ekliyoruz. - ister olarak istediği için ekledim sırf. Şifreyi açıkta vermek istemiyorum + şifre zaten tutmuyorum burada güvenlik açığı vs olmasın diye, authtta kaldı onlar tokenda yok.
                .accountExpired(false)
                .accountLocked(false)
                .authorities(authorities)
                .build();
    }




}
