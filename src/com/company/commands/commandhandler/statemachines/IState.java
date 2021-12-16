package com.company.commands.commandhandler.statemachines;

import com.company.commands.commandhandler.messagecontext.IMessageContext;

import java.util.ArrayList;
import java.util.List;

public interface IState {
    boolean predicate(IMessageContext ctx);
    void action(IMessageContext ctx);
    List<IState> getNext();
    int id();
}
