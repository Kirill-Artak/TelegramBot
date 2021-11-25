package com.company.commands.commandhandler.messagecontext;

import com.company.database.mongoDB.mongoDBtemplates.User;

public interface IMessageContext {
    long getChatID();
    User getUser();
}
