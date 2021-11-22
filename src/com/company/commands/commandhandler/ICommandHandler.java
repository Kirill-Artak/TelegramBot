package com.company.commands.commandhandler;

import com.company.commands.commandhandler.actionhandler.IActionHandler;
import com.company.commands.commandhandler.messagecontext.IMessageContext;
import com.company.commands.commandhandler.senders.ISender;

import java.util.function.Consumer;

public interface ICommandHandler {
    void setSender(ISender sender);
    IActionHandler getStartActions();
    IActionHandler getHelpActions();
}
