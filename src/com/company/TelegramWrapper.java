package com.company;

import com.company.commands.commandhandler.ICommandStore;
import com.company.commands.commandhandler.senders.TelegramSenderWrapper;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.util.AbilityExtension;

public final class TelegramWrapper extends AbilityBot {
    public TelegramWrapper(String botToken,
                           String botUsername,
                           ICommandStore commandStore) {
        super(botToken, botUsername);

        addExtensions(
                commandStore.getTelegramCommands(new TelegramSenderWrapper(silent))
                //new TelegramStartCommand(silent),
                //new TelegramHelpCommand(silent)
        );
    }

    @Override
    public long creatorId() {
        return 0;
    }
}
