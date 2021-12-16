package com.company.commands.commandhandler;

import com.company.commands.commandhandler.senders.ISender;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.util.AbilityExtension;

import java.util.Collection;

public interface ICommandStore {
    Collection<AbilityExtension> getTelegramCommands(ISender sender, DBContext automateDB);
}