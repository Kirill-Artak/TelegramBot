package com.company.database.DBtemplates;

import com.company.database.mongoDB.IRawData;

public interface IUser extends IRawData {
    long getChatID();
    String getFirstName();
    String getTable();
}
