package com.company.commands.telegramcommands;

import org.telegram.abilitybots.api.sender.SilentSender;

public class TelegramHelpCommand extends TelegramBaseCommand {
    public TelegramHelpCommand(SilentSender silent){
        super("help",
                "Помощь",
                ctx -> silent.send(
                        "Этот бот будет помогать тебе учить большие объемы информации.📕\n ",
                        ctx.chatId()),
                ctx -> {});
    }
}
