package com.company.repositories;

import com.company.database.DBtemplates.IUser;
import com.company.database.IDatabaseCore;
import com.company.database.QueryObject;
import com.company.database.dbfields.Collections;
import com.company.database.dbfields.UserFields;
import org.bson.Document;

public class UserRepository implements IUserRepository{
    private final IDatabaseCore<Document> db;

    public UserRepository(IDatabaseCore<Document> db){
        this.db = db;
    }

    @Override
    public void registerUser(IUser user) {
        db.save(
                new QueryObject<>(
                    Collections.usersCollection,
                    user.getRawData(),
                    true,
                    new Document(UserFields.chatID, user.getChatID()))
        );
    }
}
