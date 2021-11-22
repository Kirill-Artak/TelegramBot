package com.company;

import com.company.commands.commandhandler.ICommandStore;
import com.company.commands.commandhandler.senders.TelegramSenderWrapper;
import com.company.database.IDatabaseWrapper;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.util.AbilityExtension;

public final class TelegramWrapper extends AbilityBot {
    public TelegramWrapper(String botToken,
                           String botUsername,
                           ICommandStore<AbilityExtension> commandStore) {
        super(botToken, botUsername);

        addExtensions(
                commandStore.getCommands(new TelegramSenderWrapper(silent))
                //new TelegramStartCommand(silent),
                //new TelegramHelpCommand(silent)
        );
    }

    @Override
    public long creatorId() {
        return 0;
    }
}
