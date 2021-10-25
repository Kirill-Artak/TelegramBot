package com.company.commands.commandhandler;

import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.util.AbilityExtension;

import java.util.Collection;

public interface ICommandHandler<T> {
    Collection<T> getCommands(SilentSender silentSender);
}