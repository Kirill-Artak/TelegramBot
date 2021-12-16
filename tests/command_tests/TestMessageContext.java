package command_tests;

import com.company.commands.commandhandler.messagecontext.IMessageContext;
import com.company.commands.commandhandler.senders.ISender;
import com.company.database.DBtemplates.User;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;

public record TestMessageContext(long chatID, User user) implements IMessageContext {

    @Override
    public long getChatID() {
        return chatID;
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public ISender getSender() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public BaseAbilityBot bot() {
        return null;
    }

    @Override
    public void setBot(BaseAbilityBot bot) {

    }
}
