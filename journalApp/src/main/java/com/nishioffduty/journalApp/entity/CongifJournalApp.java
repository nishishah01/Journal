package com.nishioffduty.journalApp.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
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
@Document(collection = "config_journal_app")
public class CongifJournalApp {
    private String key;
    private String value;

}