package com.company.database.mongoDB.mongoDBtemplates;

import org.bson.Document;

public class User {
    private final long chatID;
    private final String firstName;
    private final String table;


    public User(long chatID, String firstName){
        this.chatID = chatID;
        this.firstName = firstName;
        this.table = chatID + '_' + firstName;
    }

    public long getChatID(){
        return chatID;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getTable(){
        return table;
    }

    public Document getRawData(){
        return new Document("chatID", chatID)
                .append("firstName", firstName)
                .append("table", table);
    }
}
