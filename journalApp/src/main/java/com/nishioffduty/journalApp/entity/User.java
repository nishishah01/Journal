package com.nishioffduty.journalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Data //THIS WILL INCLUDE EVERYTHING: GETTERS SETTERS ALLARGS,NOARGS
@Builder
@Document(collection = "user")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private ObjectId id;
    @Indexed(unique = true)
    @NonNull
    private String userName;
    private String email;
    private boolean sentimentAnalysis;
    @NonNull
    private String password;
    @DBRef
    private List<JournalEntry> journalEntries = new ArrayList<>();

    private List<String> roles;
    //tells the user what he/she is authorized to do


}