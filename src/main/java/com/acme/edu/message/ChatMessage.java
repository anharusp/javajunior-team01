package com.acme.edu.message;

import com.acme.edu.server.command.CommandParserException;

public class ChatMessage {
    private String chid;
    private String messageText;
    private String messageType;
    private long messageDateTimeMilliseconds;

    public ChatMessage(String message, long messageDateTimeMilliseconds) {
        detectCommand(message);
        this.messageDateTimeMilliseconds = messageDateTimeMilliseconds;
    }

    private void detectCommand(String message){
        try {
            this.messageType = message.split(" ", 2)[0];
            this.messageText = message.split(" ", 2)[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("No comand detcted");
        }
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "chid='" + chid + '\'' +
                ", messageText='" + messageText + '\'' +
                ", messageType='" + messageType + '\'' +
                ", messageDateTimeMilliseconds=" + messageDateTimeMilliseconds +
                '}';
    }
}
