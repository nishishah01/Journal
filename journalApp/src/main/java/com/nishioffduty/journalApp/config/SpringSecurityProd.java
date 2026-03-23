package com.nishioffduty.journalApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("prod")
public class SpringSecurityProd {
    @Bean
    public org.springframework.security.authentication.AuthenticationManager authenticationManager(
            org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration config
    ) throws Exception {
        return config.getAuthenticationManager();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())//to avoid 403 forbidden
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/journal/**","/user/**").authenticated()//it should be authenticated
//                        .requestMatchers("/admin/**").hasRole("ADMIN")//ONLY JISKA ROLE ADMIN HAI
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // ✅ KEEP THIS (important)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


//1.A user entity to represent a user data model
//2. A repository userRepository to interact with mongodb
//3. UserDetailsService implementation to fetch all user details
//4. A configuration SecurityConfig to integrate everything with Spring Security