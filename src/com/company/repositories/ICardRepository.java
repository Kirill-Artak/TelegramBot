package com.company.repositories;

import com.company.cardtemplates.ICard;
import com.company.database.DBtemplates.IUser;
import org.bson.Document;

import java.util.Map;

public interface ICardRepository {
    void addCardToUser(IUser user, ICard card);
    Iterable<Document> getCardsNames(IUser user);
    Map<String, Object> getCardDataByName(IUser user, String name);
}
