package com.nishioffduty.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;
    @Test
    @Disabled
    void testSendMail(){
        emailService.sendEmail("nishishah0104@gmail.com","Testing java email","hi bro, how are you");
    }
}
