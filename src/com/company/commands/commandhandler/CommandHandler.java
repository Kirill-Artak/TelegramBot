package com.company.commands.commandhandler;

import com.company.commands.telegramcommands.TelegramBaseCommand;
import org.telegram.abilitybots.api.util.AbilityExtension;

import java.util.Collection;
import java.util.List;

public class CommandHandler implements ITelegramCommandHandler{
    private Collection<AbilityExtension> telegramCommands;

    @Override
    public Collection<AbilityExtension> getTelegramCommands() {
        return telegramCommands;
    }

    @Override
    public void setTelegramCommands(TelegramBaseCommand... telegramBaseCommand) {
        telegramCommands = List.of(telegramBaseCommand);
    }
}
