package com.company.commands.telegramcommands;

import com.company.commands.commandhandler.ICommandHandler;
import com.company.commands.commandhandler.actionhandler.IActionHandler;
import com.company.commands.commandhandler.messagecontext.TelegramMessageContext;
import org.telegram.abilitybots.api.sender.SilentSender;

public class TelegramHelpCommand extends TelegramBaseCommand {
    public TelegramHelpCommand(IActionHandler actions){
        super("help",
                "Помощь",
                ctx -> actions.action(new TelegramMessageContext(ctx)),
                ctx -> {});
    }
}//ctx -> silent.send("Этот бот будет помогать тебе учить большие объемы информации.📕\n ", ctx.chatId()),
