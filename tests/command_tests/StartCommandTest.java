package command_tests;

import com.company.commands.commandhandler.actionhandler.IActionHandler;
import com.company.commands.commandhandler.actionhandler.StartActionHandler;
import com.company.commands.commandhandler.messagecontext.IMessageContext;
import com.company.database.DBtemplates.IUser;
import com.company.database.DBtemplates.User;
import com.company.repositories.IUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StartCommandTest {
    private final long chatID = 12345;
    private final String firstName = "abacaba";

    private IActionHandler handler;
    private TestSender sender;
    private IMessageContext ctx;
    private final IUserRepository userRepository = user -> {};

    @BeforeEach
    public void beforeEach(){
        sender = new TestSender();
        handler = new StartActionHandler(sender, userRepository);
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
