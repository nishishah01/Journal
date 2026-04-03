package com.nishioffduty.journalApp.repository;

import com.nishioffduty.journalApp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
 class UserRepositoryImplTests {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Test
    public void testSaveNewUser(){
       userRepository.getUserForSA();
    }

}
