package com.company.repositories;

import com.company.cardtemplates.ICard;
import com.company.database.DBtemplates.IUser;
import com.company.database.IDatabaseCore;
import com.company.database.QueryObject;
import com.company.database.dbfields.CardFields;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.HashMap;
import java.util.Map;

public class CardRepository implements ICardRepository{
    private final IDatabaseCore<Document> db;

    public CardRepository(IDatabaseCore<Document> db){

        this.db = db;
    }

    @Override
    public void addCardToUser(IUser user, ICard card) {
        db.save(
                new QueryObject<>(
                        user.getTable(),
                        card.getRawData(),
                        true,
                        new Document(CardFields.name, card.getName())
                )
        );
    }

    @Override
    public Iterable<Document> getCardsNames(IUser user) {
        return db.get(
                new QueryObject<>(
                        user.getTable(),
                        new Document(),
                        false,
                        new Document(CardFields.name, 1)
                )
        );
    }

    @Override
    public Map<String, Object> getCardDataByName(IUser user, String name) {
        return db.get(
                new QueryObject<>(
                        user.getTable(),
                        new Document(CardFields.name, name),
                        false,
                        new Document(CardFields.name, 1).append(CardFields.data, 1).append(CardFields.type, 1)
                )
        ).iterator().next();
    }
}
