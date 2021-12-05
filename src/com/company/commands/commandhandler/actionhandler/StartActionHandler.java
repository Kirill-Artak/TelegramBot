package com.company.commands.commandhandler.actionhandler;

import com.company.commands.commandhandler.messagecontext.IMessageContext;
import com.company.commands.commandhandler.senders.ISender;
import com.company.database.DBtemplates.User;
import com.company.repositories.IUserRepository;

public class StartActionHandler implements IActionHandler{
    private final ISender sender;
    private final IUserRepository userRepository;

    public StartActionHandler(ISender sender, IUserRepository userRepository){
        this.sender = sender;
        this.userRepository = userRepository;
    }

    @Override
    public void action(IMessageContext ctx) {
        sender.send("Привет!\n Этот бот будет помогать тебе учить большие объемы информации.\n📕", ctx.getChatID());
    }

    @Override
    public void postAction(IMessageContext ctx) {
        userRepository.registerUser(new User(ctx.getChatID(), ctx.getUser().getFirstName()));
    }
}
