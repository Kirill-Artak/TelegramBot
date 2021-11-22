package com.company;

import com.company.commands.commandhandler.ICommandStore;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBot extends BaseTelegramBot {

    public TelegramBot(String botToken,
                       String botUsername,
                       ICommandStore<AbilityExtension> commandStore) {
        super(botToken, botUsername, commandStore);
    }

    @Override
    public void botStart() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TelegramWrapper(botToken, botUsername, commandStore));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
