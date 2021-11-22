package com.company.database.mongoDB.mongoDBtemplates;

import com.company.database.dbfields.UserFields;
import com.company.database.mongoDB.IRawData;
import org.bson.Document;

public class User implements IUser {
    private final long chatID;
    private final String firstName;
    private final String table;


    public User(long chatID, String firstName){
        this.chatID = chatID;
        this.firstName = firstName;
        this.table = firstName + '_' + chatID;
    }

    @Override
    public long getChatID(){
        return chatID;
    }

    @Override
    public String getFirstName(){
        return firstName;
    }

    @Override
    public String getTable(){
        return table;
    }

    @Override
    public Document getRawData(){
        return new Document(UserFields.chatID, chatID)
                .append(UserFields.firstName, firstName)
                .append(UserFields.table, table);
    }
}
