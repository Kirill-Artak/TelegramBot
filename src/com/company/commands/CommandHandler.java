package com.company.commands;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

public class CommandHandler {
    public final HashMap<String, String> commandsInfo = new HashMap<>();

    public CommandHandler(){
    }

    public void setCommandsFromJSON(String json){
        Gson g = new Gson();
        CommandList commandList = g.fromJson(json, CommandList.class);

        for (List<String> command : commandList.commands){
            commandsInfo.put(command.get(0), command.get(1));
        }
    }

    public void setStandardCommands(){
        commandsInfo.put("start", "Привет!\n Этот бот будет помогать тебе учить большие объемы информации.\n📕");
        commandsInfo.put("help", "Этот бот будет помогать тебе учить большие объемы информации.📕\n ");
    }

    private class CommandList {
        public List<List<String>> commands;
    }
}
