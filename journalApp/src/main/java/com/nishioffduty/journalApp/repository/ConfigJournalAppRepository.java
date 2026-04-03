package com.nishioffduty.journalApp.repository;

import com.nishioffduty.journalApp.entity.CongifJournalApp;
import com.nishioffduty.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

//bean repository ko call karega and repository mongo ko call karega
public interface ConfigJournalAppRepository extends MongoRepository<CongifJournalApp, ObjectId>
{

}

