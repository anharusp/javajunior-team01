package com.acme.edu.client;

import com.acme.edu.connection.NetConnection;
import com.acme.edu.message.ChatMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MessageProcessor {
    private NetConnection connection;
    private String inputMessage;
    private ClientEntity client;
    private static BufferedReader br = new BufferedReader (new InputStreamReader(System.in));


    public NetConnection getConnection() {
        return connection;
    }

    public MessageProcessor(NetConnection connection, ClientEntity client) {
        this.connection = connection;
        this.client = client;
    }

    public void processMessage(NetConnection clientConnection) throws IOException {
        while (clientConnection.isConnected()) {
            inputMessage = br.readLine();
            ChatMessage chatMessage = new ChatMessage(inputMessage, client.getUserId(), System.currentTimeMillis());

            if (chatMessage.getChangedId()) {
                client.setUserId(chatMessage.getChid());
                System.out.println("UserId successfully changed");
            } else {

                if (chatMessage.isCommandAvailiable()) {
                    sendMessageToServer(chatMessage.toJSON(), clientConnection);
                    if ("/exit".equals(chatMessage.getMessageType())) {
                        clientConnection.close();
                        break;
                    }
                } else {
                    System.out.println("Wrong Command! Try again");
                    continue;
                }

                clientConnection.getOutput().flush();
                System.out.println(clientConnection.getInput().readUTF());
                while (clientConnection.getInput().available() > 0) {
                    System.out.println(clientConnection.getInput().readUTF());
                }
            }
        }
        br.close();
    }

    private static void sendMessageToServer(String jsonMessage, NetConnection clientConnection) throws IOException {
        clientConnection.getOutput().writeUTF(jsonMessage);
    }

}
