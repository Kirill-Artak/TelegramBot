package com.company.commands;

public abstract class BaseCommand implements ICommandDescription {
    private final String name;
    private final String description;

    protected BaseCommand(String name, String description){
        this.name = name;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
