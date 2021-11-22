package com.company.commands.telegramcommands;

import com.company.commands.commandhandler.ICommandHandler;
import com.company.commands.commandhandler.messagecontext.TelegramMessageContext;
import com.company.database.IDatabaseWrapper;
import com.company.database.mongoDB.mongoDBtemplates.User;
import org.telegram.abilitybots.api.sender.SilentSender;

public class TelegramStartCommand extends TelegramBaseCommand {
    public TelegramStartCommand(ICommandHandler commandHandler){
        super("start",
                "Начало работы",
                ctx -> commandHandler.getStartActions().action(new TelegramMessageContext(ctx)),
                ctx -> commandHandler.getStartActions().postAction(new TelegramMessageContext(ctx)));
    }
}//ctx -> silentSender.send("Привет!\n Этот бот будет помогать тебе учить большие объемы информации.\n📕", ctx.chatId()),
//ctx -> db.registerUser(new User(ctx.chatId(), ctx.user().getFirstName())));
