package com.nishioffduty.journalApp.controller;
import com.nishioffduty.journalApp.entity.JournalEntry;
import com.nishioffduty.journalApp.entity.User;
import com.nishioffduty.journalApp.service.JournalEntryService;
import com.nishioffduty.journalApp.service.UserService;
import lombok.Data;
import org.apache.coyote.Request;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
@Data
public class JournalEntryControllerV2 {

//    public Map<Long, JournalEntry> journalEntries=new HashMap();
    @Autowired
    JournalEntryService journalEntryService;
    @Autowired
    UserService userService;

    @GetMapping //actual mapping will be : journal/abc
        public ResponseEntity<?>getAllJournalEntriesOfUser(){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User user= userService.findByUserName(userName);
        List<JournalEntry>all=user.getJournalEntries();
        if(all!=null&& !all.isEmpty()){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>( HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry){
        try{
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            String userName= authentication.getName();
            journalEntryService.saveEntry(myEntry, userName);
            return new ResponseEntity<>(myEntry, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {

        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUserName(userName);

        Optional<JournalEntry> entry = user.getJournalEntries()
                .stream()
                .filter(x -> x.getId().equals(myId))
                .findFirst();

        return entry.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean removed= journalEntryService.deleteById(myId,userName);
        if(removed){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @PutMapping("/id/{id}")
//    public JournalEntry updateJournalById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
//        JournalEntry old = journalEntryService.findById(id).orElse(null);
//        if (old != null) {
//            old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()
//                    ? newEntry.getTitle()
//                    : old.getTitle());
//            old.setContent(newEntry.getContent() != null && !newEntry.equals("")
//                    ? newEntry.getContent()
//                    : old.getContent());
//        }
//        journalEntryService.saveEntry(old);
//        return old;
//    }
@PutMapping("/id/{myId}")
public ResponseEntity<?> updateJournalById(@PathVariable ObjectId myId,
                                           @RequestBody JournalEntry newEntry) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String userName = authentication.getName();

    User user = userService.findByUserName(userName);

    // Check if this journal belongs to the logged-in user
    Optional<JournalEntry> journalEntryOptional = user.getJournalEntries()
            .stream()
            .filter(x -> x.getId().equals(myId))
            .findFirst();

    if (journalEntryOptional.isPresent()) {

        JournalEntry old = journalEntryOptional.get();

        // Update fields safely
        if (newEntry.getTitle() != null && !newEntry.getTitle().isEmpty()) {
            old.setTitle(newEntry.getTitle());
        }

        if (newEntry.getContent() != null && !newEntry.getContent().isEmpty()) {
            old.setContent(newEntry.getContent());
        }

        journalEntryService.saveEntry(old);

        return new ResponseEntity<>(old, HttpStatus.OK);
    }

    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
}
}
