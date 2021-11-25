package command_tests;

import com.company.commands.commandhandler.actionhandler.HelpActionHandler;
import com.company.commands.commandhandler.actionhandler.IActionHandler;
import com.company.commands.commandhandler.messagecontext.IMessageContext;
import com.company.commands.commandhandler.senders.ISender;
import com.company.database.mongoDB.mongoDBtemplates.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HelpCommandTest {
    private final long chatID = 12345;
    private final String firstName = "abacaba";

    private IActionHandler handler;
    private TestSender sender;
    private IMessageContext ctx;

    @BeforeEach
    public void beforeEach(){
        sender = new TestSender();
        handler = new HelpActionHandler(sender);
        ctx = new TestMessageContext(chatID, new User(chatID, firstName));
    }

    @Test
    public void helpMessageTest(){
        handler.action(ctx);

        Assertions.assertEquals("Этот бот будет помогать тебе учить большие объемы информации.📕\n ",
                sender.getMessage().getMessage(),
                "Message from HelpActionHandler does not match\n");
        Assertions.assertEquals(chatID, sender.getMessage().getChatID(),
                "ChatID had changed\n");
    }
}
