package com.company.commands.commandhandler.statemachines;

public class StateMachine implements IStateMachine{
    private final IState first;

    public StateMachine(IState first){
        this.first = first;
    }

    @Override
    public IState getFirst() {
        return first;
    }
}
