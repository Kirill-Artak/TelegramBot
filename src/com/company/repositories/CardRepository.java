package com.company.repositories;

import com.company.cardtemplates.Dictionary;
import com.company.cardtemplates.ICard;
import com.company.cardtemplates.PlainText;
import com.company.database.DBtemplates.IUser;
import com.company.database.IDatabaseCore;
import com.company.database.QueryObject;
import com.company.database.dbfields.CardFields;
import com.company.database.dbfields.CardTypes;
import org.bson.Document;

public class CardRepository implements ICardRepository{
    private final IDatabaseCore<Document> db;

    public CardRepository(IDatabaseCore<Document> db){
        this.db = db;
    }

    @Override
    public void addCardToUser(IUser user, ICard card) {
        db.trySave(
                new QueryObject<>(
                        user.getTable(),
                        card.getRawData(),
                        true,
                        new Document(CardFields.name, card.getName())
                )
        );
    }

    @Override
    public void createCard(IUser user, String name) {
        Document document = new Document(CardFields.name, name);
        db.trySave(
                new QueryObject<>(
                        user.getTable(),
                        document,
                        true,
                        document
                )
        );
    }

    @Override
    public void setTypeToCard(IUser user, String name, String type) {
        db.tryUpdate(
                new QueryObject<>(
                        user.getTable(),
                        new Document(CardFields.type, type),
                        false,
                        new Document(CardFields.name, name)
                )
        );
    }

    @Override
    public void setDataToCard(IUser user, String name, Document data) {
        db.tryUpdate(
                new QueryObject<>(
                        user.getTable(),
                        new Document(CardFields.data, data),
                        false,
                        new Document(CardFields.name, name)
                )
        );
    }

    @Override
    public void setDataToCard(IUser user, String name, String data) {
        db.tryUpdate(
                new QueryObject<>(
                        user.getTable(),
                        new Document(CardFields.data, data),
                        false,
                        new Document(CardFields.name, name)
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
    public ICard getCardDataByName(IUser user, String name) {
        Document doc = db.get(
                new QueryObject<>(
                        user.getTable(),
                        new Document(CardFields.name, name),
                        false,
                        new Document(CardFields.name, 1).append(CardFields.data, 1).append(CardFields.type, 1)
                )
        ).iterator().next();

        return switch (doc.getString(CardFields.type)) {
            case CardTypes.text -> new PlainText(
                    doc.getString(CardFields.name),
                    doc.getString(CardFields.data));
            case CardTypes.dictionary -> new Dictionary(
                    doc.getString(CardFields.name),
                    doc.get(CardFields.data, Document.class));
            default -> null;
        };
    }
}
