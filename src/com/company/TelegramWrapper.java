package com.company;

import com.company.commands.commandhandler.ICommandStore;
import com.company.commands.commandhandler.senders.TelegramSenderWrapper;
import org.telegram.abilitybots.api.bot.AbilityBot;


public final class TelegramWrapper extends AbilityBot {
    public TelegramWrapper(String botToken,
                           String botUsername,
                           ICommandStore commandStore) {
        super(botToken, botUsername);

        addExtensions(
                commandStore.getTelegramCommands(
                        new TelegramSenderWrapper(silent()),
                        db())
        );
    }

    @Override
    public long creatorId() {
        return 0;
    }
}
