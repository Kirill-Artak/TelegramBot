package com.company.commands;

import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.util.AbilityExtension;

public abstract class ServiceCommand implements AbilityExtension {
    protected SilentSender silent;
    protected CommandHandler commandHandler;

    protected ServiceCommand(SilentSender silent, CommandHandler commandHandler){
        this.silent = silent;
        this.commandHandler = commandHandler;
    }

    public abstract Ability executeCommand();
}
