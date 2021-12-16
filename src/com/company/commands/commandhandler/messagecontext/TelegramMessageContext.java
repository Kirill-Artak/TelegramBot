package com.company.commands.commandhandler.messagecontext;

import com.company.commands.commandhandler.senders.ISender;
import com.company.commands.commandhandler.senders.TelegramSenderWrapper;
import com.company.database.DBtemplates.User;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.meta.api.objects.Update;


public final class TelegramMessageContext implements IMessageContext{
    private final long chatID;
    private final String firstName;
    private ISender sender;
    private String message;

    public TelegramMessageContext(MessageContext ctx){
        this.chatID = ctx.chatId();
        this.firstName = ctx.user().getFirstName();
        this.message = ctx.update().getMessage().getText();
    }

    public TelegramMessageContext(long chatID, String firstName){
        this.chatID = chatID;
        this.firstName = firstName;
    }

    public TelegramMessageContext(BaseAbilityBot bot, Update upd){
        this.sender = new TelegramSenderWrapper(bot.silent());
        this.chatID = upd.getMessage().getChatId();
        this.firstName = upd.getMessage().getFrom().getFirstName();
        this.message = upd.getMessage().getText();
    }

    public TelegramMessageContext(Update upd){
        this.chatID = upd.getMessage().getChatId();
        this.firstName = upd.getMessage().getFrom().getFirstName();
        this.message = upd.getMessage().getText();
    }

    @Override
    public long getChatID() {
        return chatID;
    }

    @Override
    public User getUser() {
        return new User(chatID, firstName);
    }

    @Override
    public ISender getSender() {
        return sender;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public BaseAbilityBot bot() {
        return bot;
    }

    @Override
    public void setBot(BaseAbilityBot bot) {
        this.bot = bot;
    }
public BaseAbilityBot bot;

}
