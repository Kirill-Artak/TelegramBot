package com.company.commands.commandhandler;

import com.company.commands.commandhandler.actionhandler.HelpActionHandler;
import com.company.commands.commandhandler.actionhandler.IActionHandler;
import com.company.commands.commandhandler.actionhandler.StartActionHandler;
import com.company.commands.commandhandler.senders.ISender;
import com.company.commands.commandhandler.statemachines.AddCommandStateMachine;
import com.company.commands.commandhandler.statemachines.GetCardStateMachine;
import com.company.commands.commandhandler.statemachines.IStateMachine;
import com.company.commands.commandhandler.statemachines.StateMachine;
import com.company.repositories.ICardRepository;
import com.company.repositories.IUserRepository;

public final class CommandHandler implements ICommandHandler{
    private ISender sender;
    private final IUserRepository userRepository;
    private final ICardRepository cardRepository;

    public CommandHandler(IUserRepository userRepository, ICardRepository cardRepository){

        this.userRepository = userRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public void setSender(ISender sender) {
        this.sender = sender;
    }

    @Override
    public IActionHandler getStartActions() {
        return new StartActionHandler(sender, userRepository);
    }

    @Override
    public IActionHandler getHelpActions() {
        return new HelpActionHandler(sender);
    }

    @Override
    public IStateMachine getAddAction() {
        return new AddCommandStateMachine(cardRepository, userRepository);
    }

    @Override
    public IStateMachine getGetCardAction() {
        return new GetCardStateMachine(cardRepository);
    }
}
