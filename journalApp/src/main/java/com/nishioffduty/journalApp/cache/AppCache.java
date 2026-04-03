package com.nishioffduty.journalApp.cache;

import com.nishioffduty.journalApp.entity.CongifJournalApp;
import com.nishioffduty.journalApp.repository.ConfigJournalAppRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {
    @Autowired
    private ConfigJournalAppRepository congifJournalAppRepository;

    public Map<String, String> APP_CACHE=new HashMap<>();


    @PostConstruct
    public void init(){
        List<CongifJournalApp>all=congifJournalAppRepository.findAll();
        for(CongifJournalApp congifJournalApp:all)
        {
            APP_CACHE.put(congifJournalApp.getKey(),congifJournalApp.getValue());
        }
    }
}
