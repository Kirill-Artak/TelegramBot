package com.company.cardtemplates;

import org.bson.Document;

import java.util.Map;

public class Dictionary extends BaseCardTemplate{
    private final Map<String, Object> data;

    public Dictionary(String name, Map<String, Object> data){
        super(name);
        this.data = data;
    }

    @Override
    public Document getRawData() {
        return rawData.append("data", new Document(data));
    }
}
