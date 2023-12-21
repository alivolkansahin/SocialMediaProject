package com.socialmedia.config.security;

import com.socialmedia.exception.AuthManagerException;
import com.socialmedia.exception.ErrorType;
import com.socialmedia.utility.JWTTokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JWTTokenManager jwtTokenManager;

    @Autowired
    private JwtUserDetails jwtUserDetails;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String bearerToken = request.getHeader("Authorization");
        if(Objects.nonNull(bearerToken) && bearerToken.startsWith("Bearer ")){
            Long authId = jwtTokenManager.getIdFromToken(bearerToken.substring(7)).orElseThrow(() -> new AuthManagerException(ErrorType.INVALID_TOKEN));
            UserDetails userDetails = jwtUserDetails.loadUserByAuthId(authId);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());  // principal, credentials, authorities

            // tokeni springe aktarma kısmı:
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
