package com.nishioffduty.journalApp.service;

import com.nishioffduty.journalApp.entity.JournalEntry;
import com.nishioffduty.journalApp.entity.User;
import com.nishioffduty.journalApp.repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository; //we are injecting
    @Autowired
    private UserService userService;

//    private static final Logger logger= LoggerFactory.getLogger(JournalEntryService.class);
//    //LoggerFactory is a utility class


    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName){
        try{
            User user= userService.findByUserName(userName);
            JournalEntry saved= journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(saved);
//            user.setUserName(null);
            userService.saveEntry(user);
        }
        catch(Exception e){

//            logger.info("hahahaha");
            throw new RuntimeException("An error occured while saving the entry",e);
        }
    }

    public void saveEntry(JournalEntry journalEntry){
        journalEntryRepository.save(journalEntry);
    }


    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId id){
        return journalEntryRepository.findById(id);
    }
    public void deleteById(ObjectId id){
        journalEntryRepository.deleteById(id);
    }
    public JournalEntry updateEntry(ObjectId id, JournalEntry newEntry){
        Optional<JournalEntry> oldEntryOptional = journalEntryRepository.findById(id);
        if(oldEntryOptional.isPresent()){
            JournalEntry oldEntry = oldEntryOptional.get();
            if(newEntry.getTitle() != null && !newEntry.getTitle().equals("")) {
                oldEntry.setTitle(newEntry.getTitle());
            }
            if(newEntry.getContent() != null && !newEntry.getContent().equals("")){
                oldEntry.setContent(newEntry.getContent());
            }
            return journalEntryRepository.save(oldEntry);
        }
        return null;
    }
    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean removed=false;
        try{
            User user= userService.findByUserName(userName);
             removed= user.getJournalEntries().removeIf(x->x.getId().equals(id));
            if(removed) {
                userService.saveEntry(user);
                journalEntryRepository.deleteById(id);
            }
        }
        catch(Exception e){
            log.error("Error",e);
            throw new RuntimeException("An error occured while deleting the entry",e);
        }
        return removed;
    }
    public List<JournalEntry> findByUserName(String username){
        User user = userService.findByUserName(username);
        if(user != null){
            return user.getJournalEntries();
        }
        return null;
    }
}
