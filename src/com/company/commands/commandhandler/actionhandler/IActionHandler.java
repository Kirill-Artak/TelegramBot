package com.company.commands.commandhandler.actionhandler;

import com.company.commands.commandhandler.messagecontext.IMessageContext;

public interface IActionHandler {
    void action(IMessageContext ctx);
    void postAction(IMessageContext ctx);
}
