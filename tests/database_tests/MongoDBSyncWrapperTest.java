package database_tests;

import com.company.cardtemplates.Dictionary;
import com.company.cardtemplates.ICard;
import com.company.cardtemplates.PlainText;
import com.company.database.dbfields.CardFields;
import com.company.database.dbfields.Collections;
import com.company.database.dbfields.UserFields;
import com.company.database.mongoDB.MongoDBSyncWrapper;
import com.company.database.mongoDB.mongoDBtemplates.IUser;
import com.company.database.mongoDB.mongoDBtemplates.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;

import java.util.Map;

public class MongoDBSyncWrapperTest {
    private TestDBWrapper db;


    @BeforeEach
    public void beforeTestMethod(){
        Map<String, String> envVars = System.getenv();
        db = new TestDBWrapper(envVars.get("MONGODB"), envVars.get("TEST_DB_NAME"));
    }

    @AfterEach
    public void afterTestMethod(){
        db.dbDrop();
    }

    @Test
    public void insertOnlyOneUserDocumentTest(){
        IUser user = new User(12345, "abacaba");
        db.registerUser(user);

        Assertions.assertEquals(1, db.getDocumentsCount(Collections.usersCollection),
                "registerUser method insert more than one element");
    }

    @Test
    public void insertUserDocumentTest(){
        IUser user = new User(12345, "abacaba");
        db.registerUser(user);

        Document documentFromDB = db.getAllFromCollection(Collections.usersCollection).first();

        Assertions.assertNotNull(documentFromDB,
                "documentFromDB is Null\n");
        Assertions.assertEquals(user.getChatID(), documentFromDB.getLong(UserFields.chatID),
                "ChatID is not correct\n\n" + documentFromDB.toJson());
        Assertions.assertEquals(user.getFirstName(), documentFromDB.getString(UserFields.firstName),
                "FirstName is nit correct\n\n" + documentFromDB.toJson());
        Assertions.assertEquals(user.getTable(), documentFromDB.getString(UserFields.table),
                "Table is not correct\n\n" + documentFromDB.toJson());
    }

    @Test
    public void sameChatIDInsertUserDocumentTest(){
        IUser user1 = new User(12345, "abacaba");
        IUser user2 = new User(12345, "abc");

        db.registerUser(user1);
        db.registerUser(user2);

        Assertions.assertEquals(1, db.getDocumentsCount(Collections.usersCollection),
                "Chat IDs in db is not unique");
    }

    @Test
    public void sameFirstNamesInsertUserDocumentTest(){
        IUser user1 = new User(12345, "abacaba");
        IUser user2 = new User(54321, "abacaba");

        db.registerUser(user1);
        db.registerUser(user2);

        Assertions.assertEquals(2, db.getDocumentsCount(Collections.usersCollection),
                "can't insert users with same first names");
    }

    @Test
    public void insertOnlyOneCardDocumentTest(){
        IUser user = new User(12345, "abacaba");
        ICard card = new PlainText("text", "abacaba");

        db.addCardToUser(user, card);

        Assertions.assertEquals(1, db.getDocumentsCount(user.getTable()),
                "addCardToUser method insert more than one element");
    }

    @Test
    public void insertCardDocumentTest(){
        IUser user = new User(12345, "abacaba");
        ICard card = new PlainText("text", "abacaba");
        db.addCardToUser(user, card);

        Document documentFromDB = db.getAllFromCollection(user.getTable()).first();

        Assertions.assertNotNull(documentFromDB,
                "documentFromDB is Null\n");
        Assertions.assertEquals(card.getName(), documentFromDB.getString(CardFields.name),
                "Name is not correct\n\n" + documentFromDB.toJson());
        Assertions.assertEquals(card.getCardType(), documentFromDB.getString(CardFields.type),
                "Type is not correct\n\n" + documentFromDB.toJson());
        Assertions.assertEquals(card.getCardData(), documentFromDB.get(CardFields.data),
                "Data is not correct\n\n" + documentFromDB.toJson());
    }

    @Test
    public void sameCardNamesInsertionCardDocumentTest(){
        IUser user = new User(12345, "abacaba");
        ICard card1 = new PlainText("text", "1");
        ICard card2 = new PlainText("text", "2");
        ICard card3 = new Dictionary("text", new Document());
        
        db.addCardToUser(user, card1);
        db.addCardToUser(user, card2);
        db.addCardToUser(user, card3);
        
        Assertions.assertEquals(1, db.getDocumentsCount(user.getTable()), 
                "Card names in db is not unique");
    }
    
    @Test
    public void countGetCarsNamesTest(){
        IUser user = new User(12345, "abacaba");
        for (int i = 0; i < 10; i++){
            ICard card = new PlainText(String.valueOf(i), "abacaba");
            db.addCardToUser(user, card);
        }

        int count = 0;

        for (Document e: db.getCardsNames(user)) {
            count++;
        }
        
        Assertions.assertEquals(10, count,
                "Count of names not equals count of cards");
    }

    @Test
    public void getCardNamesTest(){
        IUser user = new User(12345, "abacaba");
        for (int i = 0; i < 10; i++){
            ICard card = new PlainText(String.valueOf(i), "abacaba");
            db.addCardToUser(user, card);
        }

        int name = 0;

        for (Document e: db.getCardsNames(user)) {
            Assertions.assertEquals(String.valueOf(name), e.getString(CardFields.name),
                    "Card name from db not equals real card name");
            name++;
        }
    }

    @Test
    public void getCardDataByName(){
        IUser user = new User(12345, "abacaba");
        ICard card = new PlainText("text", "abacaba");
        db.addCardToUser(user, card);

        Map<String, Object> data = db.getCardDataByName(user, "text");

        Assertions.assertEquals("PlainText", data.get(CardFields.type),
                "Card type not equals real card type");
        Assertions.assertEquals("abacaba", data.get(CardFields.data),
                "Card data not equals real card data");
    }



    private class TestDBWrapper extends MongoDBSyncWrapper{

        public TestDBWrapper(String token, String dbName) {
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
