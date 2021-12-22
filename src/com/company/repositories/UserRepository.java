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
        db.trySave(
                new QueryObject<>(
                    Collections.usersCollection,
                    user.getRawData(),
                    true,
                    new Document(UserFields.chatID, user.getChatID()))
        );
    }

    @Override
    public void updateCurrentCard(IUser user, String cardName) {
        db.tryUpdate(
                new QueryObject<>(
                        Collections.usersCollection,
                        new Document(UserFields.current_card, cardName),
                        false,
                        new Document(UserFields.chatID, user.getChatID())
                )
        );
    }

    @Override
    public String getCurrentCard(IUser user) {
        return db.get(
                new QueryObject<>(
                        Collections.usersCollection,
                        new Document(UserFields.chatID, user.getChatID()),
                        false,
                        new Document(UserFields.current_card, 1)
                )
        ).iterator().next().getString(UserFields.current_card);
    }
}
