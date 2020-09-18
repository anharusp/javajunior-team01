package com.acme.edu.message;

import com.acme.edu.client.Client;
import com.acme.edu.client.ClientEntity;
import com.google.gson.Gson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The {@code ChatMessage} class represents message which user wants to send from Client side/
 */
public class ChatMessage {
    private static Gson gson = new Gson();
    private String messageText;
    private String messageType;
    private long messageDateTimeMilliseconds;
    private ClientEntity clientEntity = new ClientEntity();
    private boolean changedId = false;
    private boolean changedRoom = false;
    private String reciever;
    private int histNumber = -1;

    /**
     * Create {@code ChatMessage} instance
     * @param {@code String} message
     * @param {@code String} id is user id
     * @param {@code long} messageDateTimeMilliseconds
     */
    public ChatMessage(String message, String id, long messageDateTimeMilliseconds) {
        this.clientEntity.setUserId(id);
        detectCommand(message);
        this.messageDateTimeMilliseconds = messageDateTimeMilliseconds;
    }

    /**
     * @return {@code String} presentation of message in format "date&time user id message"
     */
    /**
     * @return {@code String} presentation of message in format "date&time user id message"
     */
    @Override
    public String toString() {
        if (reciever != null) return decorateDateTime() + decoratedIdWithReciever() + messageText;
        return decorateDateTime() + decoratedId() + messageText;
    }

    public int getHistNumber() {
        return histNumber;
    }

    /**
     * @return {@code String} current user id
     */
    public String getChid() {
        return clientEntity.getUserId();
    }

    public String getRoom() {
        return clientEntity.getRoomId();
    }

    /**
     * @return {@code boolean}
     */
    public boolean isCommandAvailiable(){
        return "/snd".equals(messageType) || "/hist".equals(messageType) || "/exit".equals(messageType) || "/chid".equals(messageType)
                || "/sndp".equals(messageType) || "/chroom".equals(messageType);
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

    public boolean isChangedRoom() {
        return changedRoom;
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
                    this.clientEntity.setUserId(message.split(" ", 2)[1]);
                    this.changedId = true;
                    break;
                case "/exit":
                    break;
                case "/sndp":
                    this.reciever = message.split(" ", 3)[1];
                    this.messageText = message.split(" ", 3)[2];
                    break;
                case "/hist":
                    try {
                        this.histNumber = Integer.parseInt(message.split(" ", 2)[1]);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        this.histNumber = -1;
                    }
                    System.out.println(histNumber);
                    break;
                case "/chroom":
                    this.clientEntity.setRoomId(message.split(" ", 2)[1]);
                    this.changedRoom = true;
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            if (this.messageType != "/hist") this.messageType = "/non";
        }
    }

    private String decorateDateTime() {
        Date currentDate = new Date(messageDateTimeMilliseconds);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss ");
        return df.format(currentDate);
    }

    private String decoratedId() {
        return "(" + clientEntity.getUserId() + "): ";
    }

    private String decoratedIdWithReciever() {
        return "(" + clientEntity.getUserId() + "->" + reciever + "): ";
    }
}
