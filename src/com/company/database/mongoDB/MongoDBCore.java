package com.company.database.mongoDB;

import com.company.database.IDatabaseCore;
import com.company.database.QueryObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBCore implements IDatabaseCore<Document> {
    protected final MongoClient mongoClient;
    protected final String dbName;

    public MongoDBCore(String token, String dbName){
        this.dbName = dbName;
        ConnectionString connectionString = new ConnectionString(token);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        mongoClient = MongoClients.create(settings);
    }

    @Override
    public boolean trySave(QueryObject<Document> query) {
        MongoDatabase db = mongoClient.getDatabase(dbName);
        MongoCollection<Document> collection = db.getCollection(query.collection());

        if (!query.isUnique() || collection.find(query.template()).first() == null) {
            collection.insertOne(query.query());

            return true;
        }

        return false;
    }

    @Override
    public Iterable<Document> get(QueryObject<Document> query) {
        MongoDatabase db = mongoClient.getDatabase(dbName);
        MongoCollection<Document> collection = db.getCollection(query.collection());

        return collection.find(query.query()).projection(query.template());
    }

    @Override
    public boolean tryUpdate(QueryObject<Document> query) {
        MongoDatabase db = mongoClient.getDatabase(dbName);
        MongoCollection<Document> collection = db.getCollection(query.collection());

        Document document = collection.findOneAndUpdate(query.template(), new Document("$set", query.query()));

        return document == null;
    }
}
