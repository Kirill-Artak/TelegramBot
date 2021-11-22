package com.company.database;

import com.company.cardtemplates.ICard;
import com.company.database.mongoDB.IRawData;
import com.company.database.mongoDB.mongoDBtemplates.IUser;
import com.company.database.mongoDB.mongoDBtemplates.User;
import org.bson.Document;

import java.util.Map;

public interface IDatabaseWrapper {
    void registerUser(IUser user);
    void addCardToUser(IUser user, ICard card);
    Iterable<Document> getCardsNames(IUser user);
    Map<String, Object> getCardDataByName(IUser user, String name);
}
