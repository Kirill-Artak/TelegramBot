package com.company.commands.commandhandler.messagecontext;


import com.company.commands.commandhandler.senders.ISender;
import com.company.database.DBtemplates.User;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.MessageContext;

public interface IMessageContext {
    long getChatID();
    User getUser();
    ISender getSender();
    String getMessage();
    BaseAbilityBot bot();
    void setBot(BaseAbilityBot bot);
}
