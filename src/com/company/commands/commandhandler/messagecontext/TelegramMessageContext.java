package com.company.commands.commandhandler.messagecontext;

import org.telegram.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.meta.api.objects.User;

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
        return ctx.user();
    }
}
