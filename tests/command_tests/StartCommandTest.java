package command_tests;

import com.company.cardtemplates.ICard;
import com.company.commands.commandhandler.actionhandler.HelpActionHandler;
import com.company.commands.commandhandler.actionhandler.IActionHandler;
import com.company.commands.commandhandler.actionhandler.StartActionHandler;
import com.company.commands.commandhandler.messagecontext.IMessageContext;
import com.company.database.DBtemplates.IUser;
import com.company.database.DBtemplates.User;
import com.company.database.IDatabaseWrapper;
import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class StartCommandTest {
    private final long chatID = 12345;
    private final String firstName = "abacaba";

    private IActionHandler handler;
    private TestSender sender;
    private IMessageContext ctx;
    private final IDatabaseWrapper<?, ?> db = new IDatabaseWrapper<Document, FindIterable<Document>>() {

        @Override
        public void insert(String collectionName, Document value, Document template) {}

        @Override
        public FindIterable<Document> get(String collectionName, Document filter) {return null;}

        @Override
        public void registerUser(IUser user) {}

        @Override
        public void addCardToUser(IUser user, ICard card) {}

        @Override
        public Iterable<Document> getCardsNames(IUser user) {return null;}

        @Override
        public Map<String, Object> getCardDataByName(IUser user, String name) {return null;}
    };

    @BeforeEach
    public void beforeEach(){
        sender = new TestSender();
        handler = new StartActionHandler(sender, db);
        ctx = new TestMessageContext(chatID, new User(chatID, firstName));
    }

    @Test
    public void startMessageTest(){
        handler.action(ctx);

        Assertions.assertEquals("Привет!\n Этот бот будет помогать тебе учить большие объемы информации.\n📕",
                sender.getMessage().getMessage(),
                "Message from HelpActionHandler does not match\n");
        Assertions.assertEquals(chatID, sender.getMessage().getChatID(),
                "ChatID had changed\n");
    }
}
