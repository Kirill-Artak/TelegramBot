package repositories_tests;

import com.company.cardtemplates.Dictionary;
import com.company.cardtemplates.ICard;
import com.company.cardtemplates.PlainText;
import com.company.database.DBtemplates.IUser;
import com.company.database.DBtemplates.User;
import com.company.database.dbfields.CardFields;
import com.company.database.mongoDB.MongoDBCore;
import com.company.repositories.CardRepository;
import com.company.repositories.ICardRepository;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import database_tests.MongoDBCoreTests;
import org.bson.Document;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class CardRepositoryTests {
    private TestDB db;
    private ICardRepository cardRepository;

    @BeforeEach
    public void beforeTestMethod(){
        Map<String, String> envVars = System.getenv();
        db = new TestDB(envVars.get("MONGODB"), envVars.get("TEST_DB_NAME"));
        cardRepository = new CardRepository(db);
    }

    @AfterEach
    public void afterTestMethod(){
        db.dbDrop();
    }


    @Test
    public void insertOnlyOneCardDocumentTest(){
        IUser user = new User(12345, "abacaba");
        ICard card = new PlainText("text", "abacaba");

        cardRepository.addCardToUser(user, card);

        Assertions.assertEquals(1, db.getDocumentsCount(user.getTable()),
                "addCardToUser method insert more than one element");
    }

    @Test
    public void insertCardDocumentTest(){
        IUser user = new User(12345, "abacaba");
        ICard card = new PlainText("text", "abacaba");
        cardRepository.addCardToUser(user, card);

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

        cardRepository.addCardToUser(user, card1);
        cardRepository.addCardToUser(user, card2);
        cardRepository.addCardToUser(user, card3);

        Assertions.assertEquals(1, db.getDocumentsCount(user.getTable()),
                "Card names in db is not unique");
    }

    @Test
    public void countGetCarsNamesTest(){
        IUser user = new User(12345, "abacaba");
        for (int i = 0; i < 10; i++){
            ICard card = new PlainText(String.valueOf(i), "abacaba");
            cardRepository.addCardToUser(user, card);
        }

        int count = 0;

        for (Document e: cardRepository.getCardsNames(user)) {
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
            cardRepository.addCardToUser(user, card);
        }

        int name = 0;

        for (Document e: cardRepository.getCardsNames(user)) {
            Assertions.assertEquals(String.valueOf(name), e.getString(CardFields.name),
                    "Card name from db not equals real card name");
            name++;
        }
    }

    @Test
    public void getCardDataByName(){
        IUser user = new User(12345, "abacaba");
        ICard card = new PlainText("text", "abacaba");
        cardRepository.addCardToUser(user, card);

        Map<String, Object> data = cardRepository.getCardDataByName(user, "text");

        Assertions.assertEquals("PlainText", data.get(CardFields.type),
                "Card type not equals real card type");
        Assertions.assertEquals("abacaba", data.get(CardFields.data),
                "Card data not equals real card data");
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
