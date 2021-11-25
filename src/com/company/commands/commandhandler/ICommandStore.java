package com.company.commands.commandhandler;

import com.company.commands.commandhandler.senders.ISender;
import com.company.database.IDatabaseWrapper;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.util.AbilityExtension;

import java.util.Collection;

public interface ICommandStore {
    Collection<AbilityExtension> getTelegramCommands(ISender sender);
}