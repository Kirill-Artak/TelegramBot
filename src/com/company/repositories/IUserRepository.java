package com.company.repositories;

import com.company.database.DBtemplates.IUser;

public interface IUserRepository {
    void registerUser(IUser user);
    void updateCurrentCard(IUser user, String cardName);
    String getCurrentCard(IUser user);
}
