package com.nishioffduty.repository;

import com.nishioffduty.entity.JournalEntry;
import com.nishioffduty.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId>
{
    User findByUserName(String username);
    void deleteByUserName(String username);
}

