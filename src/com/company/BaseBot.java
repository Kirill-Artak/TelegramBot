package com.company;

import com.company.database.IDatabaseWrapper;

public abstract class BaseBot {
    protected final String botToken;
    protected final String botUsername;
    protected final IDatabaseWrapper db;

    protected BaseBot(String botToken, String botUsername, IDatabaseWrapper db){
        this.botToken = botToken;
        this.botUsername = botUsername;
        this.db = db;
    }

    public abstract void botStart();
}