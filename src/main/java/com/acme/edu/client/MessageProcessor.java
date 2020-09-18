package com.acme.edu.client;

import com.acme.edu.connection.NetConnection;
import com.acme.edu.message.ChatMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class for processing messages
 */

public class MessageProcessor {
    private NetConnection connection;
    private String inputMessage;
    private ClientEntity client;
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Getter for connection
     *
     * @return {@code NetConnection} connection
     */
    public NetConnection getConnection() {
        return connection;
    }

    /**
     * Constructor for MessageProcessor.
     * Initializes a newly created {@code MessageProcessor} object
     * @param connection {@code NetConnection}
     * @param client {@code ClientEntity}
     */
    public MessageProcessor(NetConnection connection, ClientEntity client) {
        this.connection = connection;
        this.client = client;
    }

    /**
     * Processing message while {@code clientConnection} between Server and Client is opened.
     * @param clientConnection {@code NetConnection}
     * @throws IOException
     */
    public void processMessage(NetConnection clientConnection) throws IOException {
        while (clientConnection.isConnected()) {
            inputMessage = br.readLine();
            ChatMessage chatMessage = new ChatMessage(inputMessage, client.getUserId(), System.currentTimeMillis());

            if (chatMessage.isCommandAvailiable()) {
                if (chatMessage.getChangedId()) {
                    client.setUserId(chatMessage.getChid());
                    System.out.println("UserId successfully changed");
                }
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
            readFromServer(clientConnection);
        }
        br.close();
    }

    private void readFromServer(NetConnection clientConnection) throws IOException {
        System.out.println(clientConnection.getInput().readUTF());
        while (clientConnection.getInput().available() > 0) {
            System.out.println(clientConnection.getInput().readUTF());
        }
    }

    private static void sendMessageToServer(String jsonMessage, NetConnection clientConnection) throws IOException {
        clientConnection.getOutput().writeUTF(jsonMessage);
    }

}
