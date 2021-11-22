package com.company.commands.commandhandler;

import com.company.commands.commandhandler.actionhandler.HelpActionHandler;
import com.company.commands.commandhandler.actionhandler.IActionHandler;
import com.company.commands.commandhandler.actionhandler.StartActionHandler;
import com.company.commands.commandhandler.senders.ISender;
import com.company.database.IDatabaseWrapper;
import org.eclipse.collections.api.bag.primitive.ImmutableDoubleBag;

public final class CommandHandler implements ICommandHandler{
    private ISender sender;
    private final IDatabaseWrapper db;

    public CommandHandler(IDatabaseWrapper db){
        this.db = db;
    }

    @Override
    public void setSender(ISender sender) {
        this.sender = sender;
    }

    @Override
    public IActionHandler getStartActions() {
        return new StartActionHandler(sender, db);
    }

    @Override
    public IActionHandler getHelpActions() {
        return new HelpActionHandler(sender);
    }
}
