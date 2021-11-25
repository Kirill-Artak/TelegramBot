package com.company;

import com.company.database.IDatabaseWrapper;

public abstract class BaseBot {
    protected final String botToken;
    protected final String botUsername;

    protected BaseBot(String botToken, String botUsername){
        this.botToken = botToken;
        this.botUsername = botUsername;
    }

    public abstract void botStart();
}