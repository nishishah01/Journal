package com.nishioffduty.journalApp.controller;

import com.nishioffduty.journalApp.api.response.WeatherResponse;
import com.nishioffduty.journalApp.entity.JournalEntry;
import com.nishioffduty.journalApp.entity.User;
import com.nishioffduty.journalApp.repository.UserRepository;
import com.nishioffduty.journalApp.service.JournalEntryService;
import com.nishioffduty.journalApp.service.UserService;
import com.nishioffduty.journalApp.service.WeatherService;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@Data
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository; //we are injecting
    @Autowired
    private WeatherService weatherService;

//    @GetMapping
//    public List<User> getAllUsers(){
//        return userService.getAll();
//    }
//we will make an admin, cause one user cannot view all the users

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.saveNewUser(user);
    }//in PublicController

    @PutMapping()
    public ResponseEntity<?>updateUser(@RequestBody User user){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
    User userInDb= userService.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveNewUser(userInDb);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @DeleteMapping
    public ResponseEntity<?> deleteUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping
    public ResponseEntity<?> greeting() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greeting = "";

        if (weatherResponse != null && weatherResponse.getCurrent() != null) {
            greeting = " Weather feels like " + weatherResponse.getCurrent().getFeelsLike();
        }

        return new ResponseEntity<>("Hi " + authentication.getName() + greeting, HttpStatus.OK);
    }
}
