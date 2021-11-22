package com.company.cardtemplates;

import com.company.database.dbfields.CardFields;
import com.company.database.mongoDB.IRawData;
import org.bson.Document;

public abstract class BaseCardTemplate implements ICard {
    private final String name;
    protected final Document rawData;

    protected BaseCardTemplate(String name){
        this.name = name;
        this.rawData = new Document(CardFields.name, name).append(CardFields.type, getCardType());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCardType() {
        return this.getClass().getSimpleName();
    }
}
