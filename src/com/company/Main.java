package com.company;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, String> envVars = System.getenv();

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            botsApi.registerBot(new Bot(envVars.get("BOT_TOKEN"), envVars.get("BOT_USERNAME")));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
