package com.company;

import com.company.commands.CommandHandler;
import com.company.commands.HelpCommand;
import com.company.commands.StartCommand;
import org.telegram.abilitybots.api.bot.AbilityBot;

public final class Bot extends AbilityBot {
    public Bot(String botToken, String botUsername, CommandHandler commandHandler) {
        super(botToken, botUsername);

        addExtensions(
                new StartCommand(silent, commandHandler),
                new HelpCommand(silent, commandHandler)
        );
    }

    @Override
    public long creatorId() {
        return 0;
    }
}
