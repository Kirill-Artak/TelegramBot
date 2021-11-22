package com.company;

import com.company.commands.commandhandler.ICommandStore;
import org.telegram.abilitybots.api.util.AbilityExtension;

public abstract class BaseTelegramBot extends BaseBot {
    protected final ICommandStore<AbilityExtension> commandStore;
    protected BaseTelegramBot(String botToken,
                              String botUsername,
                              ICommandStore<AbilityExtension> commandHandler) {
        super(botToken, botUsername);
        this.commandStore = commandHandler;
    }
}
