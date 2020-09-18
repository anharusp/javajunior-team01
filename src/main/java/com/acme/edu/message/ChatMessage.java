package com.acme.edu.message;

import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The {@code ChatMessage} class represents message which user wants to send from Client side/
 */
public class ChatMessage {
    private static Gson gson = new Gson();
    private String chid;
    private String messageText;
    private String messageType;
    private long messageDateTimeMilliseconds;
    private boolean changedId = false;

    /**
     * Create {@code ChatMessage} instance
     * @param {@code String} message
     * @param {@code String} id is user id
     * @param {@code long} messageDateTimeMilliseconds
     */
    public ChatMessage(String message, String id, long messageDateTimeMilliseconds) {
        this.chid = id;
        detectCommand(message);
        this.messageDateTimeMilliseconds = messageDateTimeMilliseconds;
    }

    /**
     * @return {@code String} presentation of message in format "date&time user id message"
     */
    @Override
    public String toString() {
        return decorateDateTime() + decoratedId() + messageText;
    }

    /**
     * @return {@code String} current user id
     */
    public String getChid() {
        return chid;
    }

    /**
     * @return {@code boolean}
     */
    public boolean isCommandAvailiable(){
        return "/snd".equals(messageType) || "/hist".equals(messageType) || "/exit".equals(messageType) || "/chid".equals(messageType);
    }

    /**
     * @return {@code String} covering to JSON format
     */
    public String toJSON() {
        return gson.toJson(this);
    }

    public ChatMessage fromJSON(String json) {
        return gson.fromJson(json, ChatMessage.class);
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

    /**
     * @return {@code String} current user id
     */
    public boolean getChangedId() {
        return changedId;
    }

    /**
     * Parsing command
     * @param {@code String} message
     */
    private void detectCommand(String message){
        try {
            this.messageType = message.split(" ", 2)[0];
            switch (messageType) {
                case "/snd":
                    this.messageText = message.split(" ", 2)[1];
                    break;
                case "/chid":
                    this.chid = message.split(" ", 2)[1];
                    this.changedId = true;
                    break;
                case "/exit":
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("No such command detected");
        }
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
