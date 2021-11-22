package com.company.database.mongoDB;

import com.company.cardtemplates.ICard;
import com.company.database.IDatabaseWrapper;
import com.company.database.dbfields.CardFields;
import com.company.database.dbfields.Collections;
import com.company.database.dbfields.UserFields;
import com.company.database.mongoDB.mongoDBtemplates.IUser;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Projections;
import org.bson.Document;

import java.util.Map;

public class MongoDBSyncWrapper implements IDatabaseWrapper {
    protected final MongoClient mongoClient;
    protected final String dbName;

    public MongoDBSyncWrapper(String token, String dbName){
        this.dbName = dbName;
        ConnectionString connectionString = new ConnectionString(token);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        mongoClient = MongoClients.create(settings);
    }

    @Override
    public void registerUser(IUser user){
        insertUnique(Collections.usersCollection, user.getRawData(), new Document(UserFields.chatID, user.getChatID()));
    }

    @Override
    public void addCardToUser(IUser user, ICard card){
        insertUnique(user.getTable(), card.getRawData(), new Document(CardFields.name, card.getName()));
    }

    @Override
    public Iterable<Document> getCardsNames(IUser user){
        MongoDatabase db = mongoClient.getDatabase(dbName);
        MongoCollection<Document> collection = db.getCollection(user.getTable());

        return collection
                .find()
                .projection(Projections.include(CardFields.name))
                .sort(new Document(CardFields.name, 1));
    }

    @Override
    public Map<String, Object> getCardDataByName(IUser user, String name){
        MongoDatabase db = mongoClient.getDatabase(dbName);
        MongoCollection<Document> collection = db.getCollection(user.getTable());

        return collection.find(new Document(CardFields.name, name))
                .projection(Projections.include(CardFields.name, CardFields.type, CardFields.data))
                .first();
    }

    private void insertUnique(String collectionName, Document document, Document template){
        MongoDatabase db = mongoClient.getDatabase(dbName);
        MongoCollection<Document> collection = db.getCollection(collectionName);

        if (collection.find(template).first() == null) {
            collection.insertOne(document);
        }
    }


    //добавить метод обновления даты
}
