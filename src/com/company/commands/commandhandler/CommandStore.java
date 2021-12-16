package com.company.commands.commandhandler;

import com.company.commands.commandhandler.senders.ISender;
import com.company.commands.telegramcommands.TelegramAddCommand;
import com.company.commands.telegramcommands.TelegramHelpCommand;
import com.company.commands.telegramcommands.TelegramStartCommand;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.util.AbilityExtension;

import java.util.ArrayList;
import java.util.Collection;

public class CommandStore implements ICommandStore {
    private final ICommandHandler commandHandler;

    public CommandStore(ICommandHandler commandHandler){
        this.commandHandler = commandHandler;
    }

    @Override
    public Collection<AbilityExtension> getTelegramCommands(ISender sender, DBContext automateDB) {
        commandHandler.setSender(sender);
        ArrayList<AbilityExtension> commands = new ArrayList<>();
        commands.add(new TelegramStartCommand(commandHandler.getStartActions()));
        commands.add(new TelegramHelpCommand(commandHandler.getHelpActions()));
        commands.add(new TelegramAddCommand(automateDB, commandHandler.getAddAction()));
        return commands;
    }
}