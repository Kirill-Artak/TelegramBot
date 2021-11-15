package com.company.database;

import com.company.cardtemplates.ICard;
import com.company.database.mongoDB.mongoDBtemplates.User;

public interface IDatabaseWrapper {
    void registerUser(User user);
    void addCardToUser(User user, ICard card);
}
