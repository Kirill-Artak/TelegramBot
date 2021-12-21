package com.company.commands.commandhandler.statemachines;

import com.company.cardtemplates.Dictionary;
import com.company.cardtemplates.PlainText;
import com.company.commands.commandhandler.messagecontext.IMessageContext;
import com.company.commands.commandhandler.senders.ISender;
import com.company.database.dbfields.CardFields;
import com.company.repositories.ICardRepository;
import com.company.repositories.IUserRepository;
import org.bson.Document;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AddCommandStateMachine implements IStateMachine{
    private final ICardRepository cards;
    private final IUserRepository users;

    public AddCommandStateMachine(ICardRepository cards, IUserRepository users){
        this.cards = cards;
        this.users = users;
    }

    @Override
    public IState getFirst() {
        IState receiveText = new Node(6) {
            @Override
            public boolean predicate(IMessageContext ctx) {
                return ctx.getMessage().length() > 0
                        && !ctx.getMessage().equalsIgnoreCase("Текст");
            }

            @Override
            public void action(IMessageContext ctx) {
                String name = users.getCurrentCard(ctx.getUser());
                cards.setDataToCard(ctx.getUser(), name, ctx.getMessage());
                ctx.getSender().send("Карточка сохранена!", ctx.getChatID());
                users.updateCurrentCard(ctx.getUser(), "");
            }
        };

        IState receiveDict = new Node(5) {
            @Override
            public boolean predicate(IMessageContext ctx) {
                return ctx.getMessage().length() > 0
                        && !ctx.getMessage().equalsIgnoreCase("Ключ -> значение");
            }

            @Override
            public void action(IMessageContext ctx) {
                String name = users.getCurrentCard(ctx.getUser());
                String[] message = ctx.getMessage().split("\n");

                if (message.length % 2 != 0){
                    return;
                }

                Map<String, Object> map = new HashMap<>();

                for (String e: message) {
                    String[] pair = e.split(" -> ");
                    map.put(pair[0], pair[1]);
                }

                cards.setDataToCard(ctx.getUser(), name, new Document(map));

                ctx.getSender().send("Карточка сохранена!", ctx.getChatID());
                users.updateCurrentCard(ctx.getUser(), "");
            }
        };

        IState text = new Node(4, receiveText) {
            @Override
            public boolean predicate(IMessageContext ctx) {
                return ctx.getMessage().contains("Текст");
            }

            @Override
            public void action(IMessageContext ctx) {
                String name = users.getCurrentCard(ctx.getUser());
                cards.setTypeToCard(ctx.getUser(), name, PlainText.class.getSimpleName());
                //ctx.getSender().send("Введите текст:", ctx.getChatID());

                ReplyKeyboardMarkup keyboardMarkup = ReplyKeyboardMarkup.builder()
                        .clearKeyboard()
                        .build();

                SendMessage msg = new SendMessage(String.valueOf(ctx.getChatID()), "Введите текст:");
                msg.setReplyMarkup(keyboardMarkup);

                try {
                    ctx.getTelegramBot().execute(msg);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        };

        IState dict = new Node(3, receiveDict) {
            @Override
            public boolean predicate(IMessageContext ctx) {
                return ctx.getMessage().contains("Ключ -> значение");
            }

            @Override
            public void action(IMessageContext ctx) {
                String name = users.getCurrentCard(ctx.getUser());
                cards.setTypeToCard(ctx.getUser(), name, Dictionary.class.getSimpleName());
                //ctx.getSender().send("Введите пары вида \"ключ -> значение\":", ctx.getChatID());

                ReplyKeyboardMarkup keyboardMarkup = ReplyKeyboardMarkup.builder()
                        .clearKeyboard()
                        .build();

                SendMessage msg = new SendMessage(String.valueOf(ctx.getChatID()),
                        "Введите пары вида \"ключ -> значение\":");
                msg.setReplyMarkup(keyboardMarkup);

                try {
                    ctx.getTelegramBot().execute(msg);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        };

        IState type = new Node(2, text, dict) {
            @Override
            public boolean predicate(IMessageContext ctx) {
                return ctx.getMessage().length() > 0;
            }

            @Override
            public void action(IMessageContext ctx) {
                users.updateCurrentCard(ctx.getUser(), ctx.getMessage());
                cards.createCard(ctx.getUser(), ctx.getMessage());


                //ctx.getSender().send("Выберите тип:", ctx.getChatID());
                KeyboardRow row = new KeyboardRow();
                row.add(new KeyboardButton("Текст"));
                row.add(new KeyboardButton("Ключ -> значение"));

                ReplyKeyboardMarkup keyboardMarkup = ReplyKeyboardMarkup.builder()
                        .keyboardRow(row)
                        .oneTimeKeyboard(true)
                        .build();

                SendMessage msg = new SendMessage(String.valueOf(ctx.getChatID()), "Выберите тип:");
                msg.setReplyMarkup(keyboardMarkup);

                try {
                    ctx.getTelegramBot().execute(msg);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        };

        return new Node(1, type) {
            @Override
            public boolean predicate(IMessageContext ctx) {
                return ctx.getMessage().contains("/add");
            }

            @Override
            public void action(IMessageContext ctx) {
                ctx.getSender().send("Введите название:", ctx.getChatID());
            }
        };
    }
}
