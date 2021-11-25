package com.company.commands.commandhandler.messagecontext;


import com.company.database.DBtemplates.User;

public interface IMessageContext {
    long getChatID();
    User getUser();
}
