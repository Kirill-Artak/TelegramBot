package com.company.cardtemplates;

import org.bson.Document;

public abstract class BaseCardTemplate implements ICard{
    private final String name;
    protected final Document rawData;

    protected BaseCardTemplate(String name){
        this.name = name;
        this.rawData = new Document("name", name).append("type", getCardType());
    }

    public String getName() {
        return name;
    }

    @Override
    public String getCardType() {
        return this.getClass().getSimpleName();
    }
}
