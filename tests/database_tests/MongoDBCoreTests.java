package database_tests;

import com.company.database.QueryObject;
import com.company.database.mongoDB.MongoDBCore;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class MongoDBCoreTests {
    private TestDB db;

    @BeforeEach
    public void beforeTestMethod(){
        Map<String, String> envVars = System.getenv();
        db = new TestDB(envVars.get("MONGODB"), envVars.get("TEST_DB_NAME"));
    }

    @AfterEach
    public void afterTestMethod(){
        db.dbDrop();
    }

    @Test
    public void insertOnlyOneDocumentTest(){
        db.save(new QueryObject<>(
                "test",
                new Document("aaa", "aaa"),
                true,
                new Document()));

        Assertions.assertEquals(1, db.getDocumentsCount("test"),
                "registerUser method insert more than one element");
    }

    @Test
    public void insertDocumentTest(){
        db.save(new QueryObject<>(
                "test",
                new Document("aaa", "bbb"),
                true,
                new Document("aaa", "bbb")));

        Document documentFromDB = db.getAllFromCollection("test").first();

        Assertions.assertNotNull(documentFromDB,
                "documentFromDB is Null\n");
        Assertions.assertEquals("bbb", documentFromDB.getString("aaa"),
                "Document field is not the same\n\n" + documentFromDB.toJson());
    }

    @Test
    public void insertUniqueDocumentsTest(){
        db.save(new QueryObject<>(
                "test",
                new Document("aaa", "bbb"),
                true,
                new Document("aaa", "bbb")));

        db.save(new QueryObject<>(
                "test",
                new Document("aaa", "bbb"),
                true,
                new Document("aaa", "bbb")));

        Assertions.assertEquals(1, db.getDocumentsCount("test"),
                "registerUser method insert more than one Unique element");
    }

    @Test
    public void getExistingDocumentTest(){
        db.save(new QueryObject<>(
                "test",
                new Document("aaa", "bbb"),
                true,
                new Document("aaa", "bbb")));

        Document document = db.get(
                new QueryObject<>(
                        "test",
                        new Document("aaa", "bbb"),
                        false,
                        new Document("aaa", 1)
                )
        ).iterator().next();

        Assertions.assertNotNull(document,
                "documentFromDB is Null\n");
        Assertions.assertEquals("bbb", document.getString("aaa"),
                "Document field is not the same\n\n" + document.toJson());
    }

    public void getNotExistingDocumentTest(){
        Document document = db.get(
                new QueryObject<>(
                        "test",
                        new Document("aaa", "bbb"),
                        false,
                        new Document("aaa", 1)
                )
        ).iterator().next();

        Assertions.assertNull(document,
                "documentFromDB is Null\n");
    }


    private class TestDB extends MongoDBCore {

        public TestDB(String token, String dbName) {
            super(token, dbName);
        }

        public void dbDrop(){
            MongoDatabase db = mongoClient.getDatabase(dbName);
            db.drop();
        }

        public long getDocumentsCount(String collectionName){
            MongoDatabase db = mongoClient.getDatabase(dbName);
            MongoCollection<Document> collection = db.getCollection(collectionName);

            return collection.countDocuments();
        }

        @NotNull
        public MongoIterable<Document> getAllFromCollection(String collectionName){
            MongoDatabase db = mongoClient.getDatabase(dbName);
            MongoCollection<Document> collection = db.getCollection(collectionName);

            return collection.find();
        }
    }
}
