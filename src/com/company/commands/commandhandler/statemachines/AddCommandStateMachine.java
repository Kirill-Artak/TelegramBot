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
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;

public class AddCommandStateMachine implements IStateMachine{
    private final ICardRepository cards;
    private final IUserRepository users;

    public AddCommandStateMachine(ICardRepository cards, IUserRepository users){
        this.cards = cards;
        this.users = users;
    }

    @Override
    public IState getFirst() {
        IState stopReceiveDict = new Node(7) {
            @Override
            public boolean predicate(IMessageContext ctx) {
                return ctx.getMessage().contains("Stop");
            }

            @Override
            public void action(IMessageContext ctx) {
                String name = users.getCurrentCard(ctx.getUser());
                ctx.getSender().send("Карточка сохранена!", ctx.getChatID());
                users.updateCurrentCard(ctx.getUser(), "");
            }
        };

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

        IState receiveDict = new Node(5, stopReceiveDict) {
            @Override
            public boolean predicate(IMessageContext ctx) {
                return true;
            }

            @Override
            public void action(IMessageContext ctx) {
                String name = users.getCurrentCard(ctx.getUser());
                String[] message = ctx.getMessage().split(" -> ");
                cards.setDataToCard(ctx.getUser(), name, new Document(message[0], message[1]));
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
                ctx.getSender().send("Введите текст:", ctx.getChatID());
            }
        };

        IState dict = new Node(3, receiveDict) {
            @Override
            public boolean predicate(IMessageContext ctx) {
                return ctx.getMessage().contains("Словарь");
            }

            @Override
            public void action(IMessageContext ctx) {
                String name = users.getCurrentCard(ctx.getUser());
                cards.setTypeToCard(ctx.getUser(), name, Dictionary.class.getSimpleName());
                ctx.getSender().send("Введите пары вида \"ключ -> значение\":", ctx.getChatID());
            }
        };

        IState type = new Node(2, text, dict) {
            @Override
            public boolean predicate(IMessageContext ctx) {
                return ctx.getMessage().length() > 0;
            }

            @Override
            public void action(IMessageContext ctx) {
                try {
                    users.updateCurrentCard(ctx.getUser(), ctx.getMessage());

                } catch (Exception e){
                    e.printStackTrace();
                }

                try {
                    cards.createCard(ctx.getUser(), ctx.getMessage());
                } catch (Exception e){
                    e.printStackTrace();
                }


                //ctx.getSender().send("Выберите тип:", ctx.getChatID());
                SendMessage msg = new SendMessage(String.valueOf(ctx.getChatID()), "Выберите тип:");
                try {
                    ctx.bot().execute(msg);
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
