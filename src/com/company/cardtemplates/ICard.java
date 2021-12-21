package com.company.cardtemplates;

import com.company.database.mongoDB.IRawData;

public interface ICard extends IRawData {
    String getName();
    String getCardType();
    String getCardData();
}
