package com.company.cardtemplates;

import com.company.database.dbfields.CardFields;
import org.bson.Document;

import java.util.Map;

public class Dictionary extends BaseCardTemplate implements IDictionary{
    private final Map<String, Object> data;

    public Dictionary(String name, Map<String, Object> data){
        super(name);
        this.data = data;
    }

    @Override
    public Document getRawData() {
        return rawData.append(CardFields.data, new Document(data));
    }

    @Override
    public String getCardData() {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, Object> e: data.entrySet()) {
            result.append(e.getKey());
            result.append(" -> ");
            result.append((String) e.getValue());
            result.append('\n');
        }

        return result.substring(0);
    }

    @Override
    public Map<String, String> getMap() {
        return null;
    }
}
