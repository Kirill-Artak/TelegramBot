package com.company;

import com.company.commands.commandhandler.ICommandHandler;
import org.telegram.abilitybots.api.util.AbilityExtension;

public abstract class BaseTelegramBot extends BaseBot {
    protected final ICommandHandler<AbilityExtension> commandHandler;
    protected BaseTelegramBot(String botToken, String botUsername, ICommandHandler<AbilityExtension> commandHandler) {
        super(botToken, botUsername);
        this.commandHandler = commandHandler;
    }
}
