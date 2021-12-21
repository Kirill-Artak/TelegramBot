package com.company.commands.commandhandler.statemachines;

import com.company.cardtemplates.ICard;
import com.company.commands.commandhandler.messagecontext.IMessageContext;
import com.company.database.dbfields.CardFields;
import com.company.repositories.ICardRepository;
import org.bson.Document;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GetCardStateMachine implements IStateMachine{
    private final ICardRepository cards;

    public GetCardStateMachine(ICardRepository cards){
        this.cards = cards;
    }

    @Override
    public IState getFirst() {
        Node getCard = new Node(8) {
            @Override
            public boolean predicate(IMessageContext ctx) {
                return ctx.getCallbackQuery() != null;
            }

            @Override
            public void action(IMessageContext ctx) {
                ICard card = cards.getCardDataByName(ctx.getUser(), ctx.getCallbackQuery());

                if (card == null) {
                    ctx.getSender().send("Упс... такой карточки нет...", ctx.getChatID());
                    return;
                }

                ctx.getSender().send(card.getCardData(), ctx.getChatID());
            }
        };

        return new Node(7, getCard) {
            @Override
            public boolean predicate(IMessageContext ctx) {
                return ctx.getMessage().contains("/getCard");
            }

            @Override
            public void action(IMessageContext ctx) {
                Iterable<Document> cardNames = cards.getCardsNames(ctx.getUser());
                //InlineKeyboardMarkup.InlineKeyboardMarkupBuilder keyboard = InlineKeyboardMarkup.builder();

                List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();

                Iterator<Document> iter = cardNames.iterator();

                for (int i = 0; i < 10; i++){
                    ArrayList<InlineKeyboardButton> ar = new ArrayList<>();

                    for (int j = 0; j < 2; j++){
                        if (!iter.hasNext())
                            break;

                        String text = iter.next().getString(CardFields.name);
                        InlineKeyboardButton btn = InlineKeyboardButton.builder()
                                .text(text)
                                .callbackData(text)
                                .build();

                        ar.add(btn);
                    }
                    keyboard.add(ar);
                    if (!iter.hasNext())
                        break;


                }

                SendMessage msg = SendMessage.builder()
                        .text("Выберите карточку")
                        .chatId(String.valueOf(ctx.getChatID()))
                        .replyMarkup(new InlineKeyboardMarkup(keyboard))
                        .build();

                try {
                    ctx.getTelegramBot().execute(msg);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
