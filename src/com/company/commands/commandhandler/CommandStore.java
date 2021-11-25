package com.company.commands.commandhandler;

import com.company.commands.commandhandler.senders.ISender;
import com.company.commands.telegramcommands.TelegramHelpCommand;
import com.company.commands.telegramcommands.TelegramStartCommand;
import com.company.database.IDatabaseWrapper;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.util.AbilityExtension;

import java.util.ArrayList;
import java.util.Collection;

public class CommandStore implements ICommandStore {
    private final ICommandHandler commandHandler;

    public CommandStore(ICommandHandler commandHandler){
        this.commandHandler = commandHandler;
    }

    @Override
    public Collection<AbilityExtension> getTelegramCommands(ISender sender) {
        commandHandler.setSender(sender);
        ArrayList<AbilityExtension> commands = new ArrayList<>();
        commands.add(new TelegramStartCommand(commandHandler.getStartActions()));
        commands.add(new TelegramHelpCommand(commandHandler.getHelpActions()));
        return commands;
    }
}