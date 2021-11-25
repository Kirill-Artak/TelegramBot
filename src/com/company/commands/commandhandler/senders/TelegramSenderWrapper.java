package com.company.commands.commandhandler.senders;

import org.telegram.abilitybots.api.sender.SilentSender;

public final class TelegramSenderWrapper implements ISender{
    private final SilentSender silent;

    public TelegramSenderWrapper(SilentSender silent){
        this.silent = silent;
    }

    @Override
    public void send(String message, long chatID) {
        silent.send(message, chatID);
    }
}
