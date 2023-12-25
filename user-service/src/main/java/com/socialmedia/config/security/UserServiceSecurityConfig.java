package com.socialmedia.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserServiceSecurityConfig {
    private final String[] WHITELIST ={"/swagger-ui/**","/v3/api-docs/**","/api/v1/user/findall"};

    @Bean
    JwtTokenFilter getJwtTokenFilter(){
        return new JwtTokenFilter();
    }

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();
        httpSecurity.authorizeRequests().antMatchers(WHITELIST).permitAll().anyRequest().authenticated();
        httpSecurity.addFilterBefore(getJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

/*//    private final JwtTokenFilter jwtTokenFilter; // bunu yapamıyorum dependency injection ile çünkü configuration sınıfı burası.... Onun yerine şöyle yapacağım.
    @Bean
    public JwtTokenFilter getJwtTokenFilter() {
        return new JwtTokenFilter();
    }

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity) throws Exception { // kendi filtremi devreye koyuyorum, şu an içi boş diye istek atınca direkt geliyor (eskisi gibi)
        httpSecurity.csrf().disable()
                .authorizeRequests()
//                .antMatchers("/mylogin.html").permitAll() //buraya gelen isteklerin hepsine izin ver, onun dışındakiler authenticate olsun diyoruz. resources public altına html sayfamızı da oluşturduk.
                .antMatchers("/swagger-ui/**", "/v3/api-docs/**","/api/v1/user/findall","/api/v1/yetki/**").permitAll()
                .antMatchers("/api/v1/yetki/viparea").hasAnyAuthority("VIP","Admin")
                .anyRequest().authenticated();
//        httpSecurity.formLogin().loginPage("/mylogin.html");
//        httpSecurity.formLogin();

        // JwtTokenFilter ile biz kendi custom filtremizi yazmış olduk!
        httpSecurity.addFilterBefore(getJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }*/

}
