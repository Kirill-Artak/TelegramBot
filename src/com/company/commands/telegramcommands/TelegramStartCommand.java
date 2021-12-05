package com.company.commands.telegramcommands;

import com.company.commands.commandhandler.actionhandler.IActionHandler;
import com.company.commands.commandhandler.messagecontext.TelegramMessageContext;

public class TelegramStartCommand extends TelegramBaseCommand {
    public TelegramStartCommand(IActionHandler actions){
        super("start",
                "Начало работы",
                ctx -> actions.action(new TelegramMessageContext(ctx)),
                ctx -> actions.postAction(new TelegramMessageContext(ctx)));
    }
}
