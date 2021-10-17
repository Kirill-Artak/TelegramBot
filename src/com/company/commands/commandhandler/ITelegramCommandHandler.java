package com.company.commands.commandhandler;

import com.company.commands.telegramcommands.TelegramBaseCommand;
import org.telegram.abilitybots.api.util.AbilityExtension;

import java.util.Collection;

public interface ITelegramCommandHandler {
    public Collection<AbilityExtension> getTelegramCommands();
    public void setTelegramCommands(TelegramBaseCommand... telegramBaseCommand);
}
