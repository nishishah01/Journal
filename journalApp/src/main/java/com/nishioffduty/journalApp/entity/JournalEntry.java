package com.nishioffduty.journalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
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
@Document(collection = "journal_entries")
public class JournalEntry {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String content;
    @DBRef //reference create kar rahe hai users collection ke andar <-- journal entries ka
    private List<JournalEntry> journalEntries=new ArrayList<>();

}