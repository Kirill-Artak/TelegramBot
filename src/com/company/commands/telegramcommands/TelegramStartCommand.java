package com.company.commands.telegramcommands;

import org.telegram.abilitybots.api.sender.SilentSender;

public class TelegramStartCommand extends TelegramBaseCommand {
    public TelegramStartCommand(SilentSender silentSender){
        super("start",
                "Начало работы",
                ctx -> silentSender.send(
                        "Привет!\n Этот бот будет помогать тебе учить большие объемы информации.\n📕",
                        ctx.chatId()),
                ctx -> {});
    }
}
