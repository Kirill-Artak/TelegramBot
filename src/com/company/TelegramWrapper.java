package com.company;

import com.company.commands.BaseCommand;
import com.company.commands.commandhandler.ICommandHandler;
import com.company.commands.telegramcommands.TelegramBaseCommand;
import com.company.commands.telegramcommands.TelegramHelpCommand;
import com.company.commands.telegramcommands.TelegramStartCommand;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.util.AbilityExtension;

public final class TelegramWrapper extends AbilityBot {
    public TelegramWrapper(String botToken, String botUsername, ICommandHandler<AbilityExtension> commandHandler) {
        super(botToken, botUsername);

        addExtensions(
                commandHandler.getCommands(silent)
                //new TelegramStartCommand(silent),
                //new TelegramHelpCommand(silent)
        );
    }

    @Override
    public long creatorId() {
        return 0;
    }
}
