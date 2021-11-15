package com.company.cardtemplates;

import org.bson.Document;

public class PlainText extends BaseCardTemplate{
    private final String text;

    public PlainText(String name, String text){
        super(name);
        this.text = text;
    }

    @Override
    public Document getRawData() {
        return rawData.append("data", text);
    }
}
