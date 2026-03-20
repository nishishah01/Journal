package com.nishioffduty.controller;

import com.nishioffduty.entity.JournalEntry;
import com.nishioffduty.entity.User;
import com.nishioffduty.repository.UserRepository;
import com.nishioffduty.service.JournalEntryService;
import com.nishioffduty.service.UserService;
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
}
