package com.company;

import com.company.commands.commandhandler.ICommandHandler;
import com.company.commands.commandhandler.TelegramCommandHandler;
import com.company.database.IDatabaseWrapper;
import org.telegram.abilitybots.api.util.AbilityExtension;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBot extends BaseTelegramBot {

    public TelegramBot(String botToken,
                       String botUsername,
                       ICommandHandler<AbilityExtension> commandHandler,
                       IDatabaseWrapper db) {
        super(botToken, botUsername, commandHandler, db);
    }

    @Override
    public void botStart() {
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new TelegramWrapper(botToken, botUsername, commandHandler, db));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
