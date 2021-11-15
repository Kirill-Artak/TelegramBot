package com.company;

import com.company.commands.commandhandler.ICommandHandler;
import com.company.database.IDatabaseWrapper;
import org.telegram.abilitybots.api.util.AbilityExtension;

public abstract class BaseTelegramBot extends BaseBot {
    protected final ICommandHandler<AbilityExtension> commandHandler;
    protected BaseTelegramBot(String botToken,
                              String botUsername,
                              ICommandHandler<AbilityExtension> commandHandler,
                              IDatabaseWrapper db) {
        super(botToken, botUsername, db);
        this.commandHandler = commandHandler;
    }
}
