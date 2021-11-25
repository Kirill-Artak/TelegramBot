package com.company.database;

import com.company.cardtemplates.ICard;
import com.company.database.DBtemplates.IUser;
import org.bson.Document;

import java.util.Map;

public interface IDatabaseWrapper<V, T extends Iterable<V>> extends IRepository<V, String, T> {
    void registerUser(IUser user);
    void addCardToUser(IUser user, ICard card);
    Iterable<Document> getCardsNames(IUser user);
    Map<String, Object> getCardDataByName(IUser user, String name);
}
