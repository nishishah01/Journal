package com.nishioffduty.journalApp.service;

import com.nishioffduty.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.mockito.Mockito.*;

import java.util.ArrayList;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("dev")
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void loadUserByUsernameTest(){

        when(userRepository.findByUserName(anyString()))
                .thenReturn(com.nishioffduty.journalApp.entity.User.builder()
                        .userName("nishi")
                        .password("pass@123")
                        .roles(new ArrayList<>())
                        .build());

        UserDetails user = userDetailsService.loadUserByUsername("nishi");

        assertNotNull(user);
    }
}

//Use of mokito is when method will be called, toh actual repository call nai hoke, this mock will be called