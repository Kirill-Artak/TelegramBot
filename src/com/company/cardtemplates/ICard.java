package com.company.cardtemplates;

import com.company.database.mongoDB.IRawData;
import org.bson.Document;

public interface ICard extends IRawData {
    String getName();
    String getCardType();
    Object getCardData();
}
