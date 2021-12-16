package com.company;

import com.company.commands.commandhandler.ICommandStore;
import com.company.commands.commandhandler.senders.TelegramSenderWrapper;
import com.company.commands.telegramcommands.TelegramAddCommand;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.db.MapDBContext;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Locality;
import org.telegram.abilitybots.api.objects.Privacy;
import org.telegram.abilitybots.api.objects.ReplyFlow;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public final class TelegramWrapper extends AbilityBot {
    public TelegramWrapper(String botToken,
                           String botUsername,
                           ICommandStore commandStore) {
        super(botToken, botUsername);

        addExtensions(
                commandStore.getTelegramCommands(new TelegramSenderWrapper(silent()), db())
                //new TelegramStartCommand(silent),
                //new TelegramHelpCommand(silent)
        );
    }

    @Override
    public long creatorId() {
        return 0;
    }
}
