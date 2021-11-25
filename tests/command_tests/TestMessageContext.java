package command_tests;

import com.company.commands.commandhandler.messagecontext.IMessageContext;
import com.company.database.mongoDB.mongoDBtemplates.User;

public record TestMessageContext(long chatID, User user) implements IMessageContext {

    @Override
    public long getChatID() {
        return chatID;
    }

    @Override
    public User getUser() {
        return user;
    }
}
