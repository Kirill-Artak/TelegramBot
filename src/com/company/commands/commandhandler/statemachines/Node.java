package com.company.commands.commandhandler.statemachines;

import com.company.commands.commandhandler.messagecontext.IMessageContext;

import java.util.ArrayList;
import java.util.List;

public abstract class Node implements IState{
    private final List<IState> next;
    private final int id;

    public Node(int id, IState... next){
        this.id = id;
        this.next = List.of(next);
    }

    @Override
    public List<IState> getNext() {
        return next;
    }

    @Override
    public int id(){
        return id;
    }
}
