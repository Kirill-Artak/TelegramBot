package com.company;

import com.company.commands.CommandHandler;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Map<String, String> envVars = System.getenv();

        CommandHandler commandHandler= new CommandHandler();
        //commandHandler.GetCommandsFromJSON("");
        commandHandler.setStandardCommands();

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            botsApi.registerBot(new Bot(
                    envVars.get("BOT_TOKEN"), envVars.get("BOT_USERNAME"),
                    commandHandler));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
