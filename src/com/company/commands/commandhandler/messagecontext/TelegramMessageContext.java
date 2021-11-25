package com.company.commands.commandhandler.messagecontext;

import com.company.database.DBtemplates.User;
import org.telegram.abilitybots.api.objects.MessageContext;


public final class TelegramMessageContext implements IMessageContext{
    private final MessageContext ctx;

    public TelegramMessageContext(MessageContext ctx){
        this.ctx = ctx;
    }

    @Override
    public long getChatID() {
        return ctx.chatId();
    }

    @Override
    public User getUser() {
        return new User(ctx.chatId(), ctx.user().getFirstName());
    }
}
