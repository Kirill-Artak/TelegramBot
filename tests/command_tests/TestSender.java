package command_tests;

import com.company.commands.commandhandler.senders.ISender;

public class TestSender implements ISender {
    private Message message;

    @Override
    public void send(String message, long chatID) {
        this.message = new Message(chatID, message);
    }

    public Message getMessage() {
        return message;
    }

    public class Message{
        private final long chatID;
        private final String message;

        protected Message(long chatID, String message){
            this.chatID = chatID;
            this.message = message;
        }

        long getChatID(){
            return chatID;
        }

        String getMessage(){
            return message;
        }
    }
}
