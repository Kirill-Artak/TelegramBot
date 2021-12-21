package com.company.repositories;

import com.company.cardtemplates.ICard;
import com.company.database.DBtemplates.IUser;
import org.bson.Document;

import java.util.Map;

public interface ICardRepository {
    void addCardToUser(IUser user, ICard card);
    void createCard(IUser user, String name);
    void setTypeToCard(IUser user, String name, String type);
    void setDataToCard(IUser user, String name, Document data);
    void setDataToCard(IUser user, String name, String data);
    Iterable<Document> getCardsNames(IUser user);
    ICard getCardDataByName(IUser user, String name);
}
