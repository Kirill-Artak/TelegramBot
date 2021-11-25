package com.company.database.mongoDB;

import com.company.cardtemplates.ICard;
import com.company.database.IDatabaseWrapper;
import com.company.database.dbfields.CardFields;
import com.company.database.dbfields.Collections;
import com.company.database.dbfields.UserFields;
import com.company.database.DBtemplates.IUser;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Projections;
import org.bson.Document;

import java.util.Map;

public class MongoDBSyncWrapper implements IDatabaseWrapper<Document, FindIterable<Document>> {
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
        insert(Collections.usersCollection, user.getRawData(), new Document(UserFields.chatID, user.getChatID()));
    }

    @Override
    public void addCardToUser(IUser user, ICard card){
        insert(user.getTable(), card.getRawData(), new Document(CardFields.name, card.getName()));
    }

    @Override
    public Iterable<Document> getCardsNames(IUser user){
        return get(user.getTable(), new Document())
                .projection(Projections.include(CardFields.name))
                .sort(new Document(CardFields.name, 1));
    }

    @Override
    public Map<String, Object> getCardDataByName(IUser user, String name){
        return get(user.getTable(), new Document(CardFields.name, name))
                .projection(Projections.include(CardFields.name, CardFields.type, CardFields.data))
                .first();
    }

    @Override
    public void insert(String collectionName, Document value, Document template) {
        MongoDatabase db = mongoClient.getDatabase(dbName);
        MongoCollection<Document> collection = db.getCollection(collectionName);

        if (collection.find(template).first() == null) {
            collection.insertOne(value);
        }
    }

    @Override
    public FindIterable<Document> get(String collectionName, Document filter) {
        MongoDatabase db = mongoClient.getDatabase(dbName);
        MongoCollection<Document> collection = db.getCollection(collectionName);

        return collection.find(filter);
    }


    //добавить метод обновления даты
}
