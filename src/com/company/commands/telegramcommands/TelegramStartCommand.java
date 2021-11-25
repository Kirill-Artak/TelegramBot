package com.company.commands.telegramcommands;

import com.company.commands.commandhandler.ICommandHandler;
import com.company.commands.commandhandler.actionhandler.IActionHandler;
import com.company.commands.commandhandler.messagecontext.TelegramMessageContext;
import com.company.database.IDatabaseWrapper;
import com.company.database.mongoDB.mongoDBtemplates.User;
import org.telegram.abilitybots.api.sender.SilentSender;

public class TelegramStartCommand extends TelegramBaseCommand {
    public TelegramStartCommand(IActionHandler actions){
        super("start",
                "Начало работы",
                ctx -> actions.action(new TelegramMessageContext(ctx)),
                ctx -> actions.postAction(new TelegramMessageContext(ctx)));
    }
}
