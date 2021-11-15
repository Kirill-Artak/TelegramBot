package com.company.cardtemplates;

import org.bson.Document;

public interface ICard {
    String getCardType();
    Document getRawData();
}
