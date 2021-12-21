package com.company.commands.commandhandler.messagecontext;

import com.company.commands.commandhandler.senders.ISender;
import com.company.commands.commandhandler.senders.TelegramSenderWrapper;
import com.company.database.DBtemplates.User;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Consumer;


public final class TelegramMessageContext implements IMessageContext{
    private long chatID;
    private final String firstName;
    private ISender sender;
    private String message;
    private BaseAbilityBot bot;
    private String callback;

    public TelegramMessageContext(MessageContext ctx){
        this.chatID = ctx.chatId();
        this.firstName = ctx.user().getFirstName();
        this.message = ctx.update().getMessage().getText();
        this.bot = ctx.bot();
        if (ctx.update().hasCallbackQuery()){
            this.callback = ctx.update().getCallbackQuery().getData();
        }
    }

    public TelegramMessageContext(long chatID, String firstName){
        this.chatID = chatID;
        this.firstName = firstName;
    }

    public TelegramMessageContext(BaseAbilityBot bot, Update upd){
        this.sender = new TelegramSenderWrapper(bot.silent());
        if (upd.hasMessage())
            this.chatID = upd.getMessage().getChatId();
        else if (upd.hasCallbackQuery())
            this.chatID = upd.getCallbackQuery().getFrom().getId().longValue();
        this.firstName = upd.getMessage().getFrom().getFirstName();
        this.message = upd.getMessage().getText();
        this.bot = bot;
        if (upd.hasCallbackQuery()){
            this.callback = upd.getCallbackQuery().getData();
        }
    }

    public TelegramMessageContext(Update upd){
        if (upd.hasMessage())
            this.chatID = upd.getMessage().getChatId();
        else if (upd.hasCallbackQuery())
            this.chatID = upd.getCallbackQuery().getFrom().getId().longValue();
        this.firstName = upd.getMessage().getFrom().getFirstName();
        this.message = upd.getMessage().getText();
        if (upd.hasCallbackQuery()){
            this.callback = upd.getCallbackQuery().getData();
        }
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
    public BaseAbilityBot getTelegramBot() {
        return bot;
    }

    @Override
    public String getCallbackQuery() {
        return callback;
    }
}
