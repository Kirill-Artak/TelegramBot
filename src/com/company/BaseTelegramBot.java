package com.company;

import com.company.commands.commandhandler.ICommandStore;
import org.telegram.abilitybots.api.util.AbilityExtension;

public abstract class BaseTelegramBot extends BaseBot {
    protected final ICommandStore commandStore;
    protected BaseTelegramBot(String botToken,
                              String botUsername,
                              ICommandStore commandHandler) {
        super(botToken, botUsername);
        this.commandStore = commandHandler;
    }
}
