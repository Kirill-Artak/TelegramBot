package com.company.database.mongoDB;

import com.company.cardtemplates.ICard;
import com.company.database.IDatabaseWrapper;
import com.company.database.mongoDB.mongoDBtemplates.User;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBSyncWrapper implements IDatabaseWrapper {
    MongoClient mongoClient;

    public MongoDBSyncWrapper(String token){
        ConnectionString connectionString = new ConnectionString(token);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        mongoClient = MongoClients.create(settings);
    }

    @Override
    public void registerUser(User user){
        MongoDatabase db = mongoClient.getDatabase("DailyEduBot");
        MongoCollection<Document> collection = db.getCollection("Users");

        if (collection.find(user.getRawData()).first() == null) {
            collection.insertOne(user.getRawData());
        }
    }

    @Override
    public void addCardToUser(User user, ICard card){
        MongoDatabase db = mongoClient.getDatabase("DailyEduBot");
        MongoCollection<Document> collection = db.getCollection(user.getTable());

        collection.insertOne(card.getRawData());
    }
}
