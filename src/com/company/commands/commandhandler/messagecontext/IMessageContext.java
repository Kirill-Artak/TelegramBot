package com.company.commands.commandhandler.messagecontext;

import org.telegram.telegrambots.meta.api.objects.User;

public interface IMessageContext {
    long getChatID();
    User getUser();
}
