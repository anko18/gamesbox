package com.gamesbox.security;

import com.gamesbox.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,  AuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {

        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/register").permitAll()
                        .requestMatchers("/js/**", "/css/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/authenticateTheUser")
                        .successHandler(customAuthenticationSuccessHandler)
                        .defaultSuccessUrl("/account", true)
                        .permitAll()
                )
                .logout((logout) -> logout.permitAll()
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID"))
        ;
        return http.build();
    }

    //authenticationProvider bean definition
    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService) {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService); //set the custom user details service
        auth.setPasswordEncoder(passwordEncoder()); //set the password encoder - bcrypt
        return auth;

    }
}
