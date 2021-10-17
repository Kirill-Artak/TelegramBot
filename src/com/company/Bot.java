package com.company;

import com.company.commands.commandhandler.ITelegramCommandHandler;
import com.company.commands.telegramcommands.TelegramHelpCommand;
import com.company.commands.telegramcommands.TelegramStartCommand;
import org.telegram.abilitybots.api.bot.AbilityBot;

public final class Bot extends AbilityBot {
    public Bot(String botToken, String botUsername, ITelegramCommandHandler telegramCommandHandler) {
        super(botToken, botUsername);

        addExtensions(
                new TelegramStartCommand(silent),
                new TelegramHelpCommand(silent)
        );
    }

    @Override
    public long creatorId() {
        return 0;
    }
}
