package com.acme.edu.message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMessage {
    private String chid;
    private String messageText;
    private String messageType;
    private long messageDateTimeMilliseconds;
    private boolean isOpen = true;

    public ChatMessage(String message, String id, long messageDateTimeMilliseconds) {
        this.chid = id;
        detectCommand(message);
        this.messageDateTimeMilliseconds = messageDateTimeMilliseconds;
    }

    @Override
    public String toString() {
        return decorateDateTime() + decoratedId() + messageText;
    }

    public String getChid() {
        return chid;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageType() {
        return messageType;
    }

    public long getMessageDateTimeMilliseconds() {
        return messageDateTimeMilliseconds;
    }

    public boolean isOpen() {
        return isOpen;
    }

    private void detectCommand(String message){
        try {
            this.messageType = message.split(" ", 2)[0];
            switch (messageType) {
                case "/snd":
                    this.messageText = message.split(" ", 2)[1];
                    break;
                case "/chid":
                    this.chid = message.split(" ", 2)[1];
                    break;
                case "/exit":
                    this.isOpen = false;
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("No such command detected");
        }
    }

    public boolean isCommandAvailiable(){
        return "/snd".equals(messageType) || "/hist".equals(messageType) || "/exit".equals(messageType) || "/chid".equals(messageType);
    }

    private String decorateDateTime() {
        Date currentDate = new Date(messageDateTimeMilliseconds);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ");
        return df.format(currentDate);
    }

    private String decoratedId() {
        return "(" + chid + "): ";
    }
}
