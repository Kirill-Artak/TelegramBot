package com.company.commands.commandhandler.senders;

public interface ISender {
    void send(String message, long chatID);
}
