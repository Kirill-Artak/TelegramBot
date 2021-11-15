package com.company.commands.commandhandler;

import com.company.commands.BaseCommand;
import com.company.commands.telegramcommands.TelegramBaseCommand;
import com.company.commands.telegramcommands.TelegramHelpCommand;
import com.company.commands.telegramcommands.TelegramStartCommand;
import com.company.database.IDatabaseWrapper;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.util.AbilityExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TelegramCommandHandler implements ICommandHandler<AbilityExtension>{

    @Override
    public Collection<AbilityExtension> getCommands(SilentSender silentSender, IDatabaseWrapper db) {
        ArrayList<AbilityExtension> commands = new ArrayList<>();
        commands.add(new TelegramStartCommand(silentSender, db));
        commands.add(new TelegramHelpCommand(silentSender));
        return commands;
    }
}