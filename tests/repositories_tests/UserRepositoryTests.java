package repositories_tests;

import com.company.database.DBtemplates.IUser;
import com.company.database.DBtemplates.User;
import com.company.database.dbfields.Collections;
import com.company.database.dbfields.UserFields;
import com.company.database.mongoDB.MongoDBCore;
import com.company.repositories.IUserRepository;
import com.company.repositories.UserRepository;
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

public class UserRepositoryTests {
    private TestDB db;
    private IUserRepository userRepository;

    @BeforeEach
    public void beforeTestMethod(){
        Map<String, String> envVars = System.getenv();
        db = new TestDB(envVars.get("MONGODB"), envVars.get("TEST_DB_NAME"));
        userRepository = new UserRepository(db);
    }

    @AfterEach
    public void afterTestMethod(){
        //db.dbDrop();
    }


    @Test
    public void insertOnlyOneUserDocumentTest(){
        IUser user = new User(12345, "abacaba");
        userRepository.registerUser(user);

        Assertions.assertEquals(1, db.getDocumentsCount(Collections.usersCollection),
                "registerUser method insert more than one element");
    }

    @Test
    public void insertUserDocumentTest(){
        IUser user = new User(12345, "abacaba");
        userRepository.registerUser(user);

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

        userRepository.registerUser(user1);
        userRepository.registerUser(user2);

        Assertions.assertEquals(1, db.getDocumentsCount(Collections.usersCollection),
                "Chat IDs in db is not unique");
    }

    @Test
    public void sameFirstNamesInsertUserDocumentTest(){
        IUser user1 = new User(12345, "abacaba");
        IUser user2 = new User(54321, "abacaba");

        userRepository.registerUser(user1);
        userRepository.registerUser(user2);

        Assertions.assertEquals(2, db.getDocumentsCount(Collections.usersCollection),
                "can't insert users with same first names");
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
