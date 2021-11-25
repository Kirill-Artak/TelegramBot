package com.company.commands.commandhandler.actionhandler;

import com.company.commands.commandhandler.messagecontext.IMessageContext;
import com.company.commands.commandhandler.senders.ISender;

public class HelpActionHandler implements IActionHandler{
    private final ISender sender;

    public HelpActionHandler(ISender sender){
        this.sender = sender;
    }

    @Override
    public void action(IMessageContext ctx) {
        sender.send("Этот бот будет помогать тебе учить большие объемы информации.📕\n ", ctx.getChatID());
    }

    @Override
    public void postAction(IMessageContext ctx) {
    }
}
