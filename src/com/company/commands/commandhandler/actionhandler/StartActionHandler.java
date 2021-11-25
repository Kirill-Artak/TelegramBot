package com.company.commands.commandhandler.actionhandler;

import com.company.commands.commandhandler.messagecontext.IMessageContext;
import com.company.commands.commandhandler.senders.ISender;
import com.company.database.DBtemplates.User;
import com.company.database.IDatabaseWrapper;

public class StartActionHandler implements IActionHandler{
    private final ISender sender;
    private final IDatabaseWrapper<?, ?> db;

    public StartActionHandler(ISender sender, IDatabaseWrapper<?, ?> db){
        this.sender = sender;
        this.db = db;
    }

    @Override
    public void action(IMessageContext ctx) {
        sender.send("Привет!\n Этот бот будет помогать тебе учить большие объемы информации.\n📕", ctx.getChatID());
    }

    @Override
    public void postAction(IMessageContext ctx) {
        db.registerUser(new User(ctx.getChatID(), ctx.getUser().getFirstName()));
    }
}
