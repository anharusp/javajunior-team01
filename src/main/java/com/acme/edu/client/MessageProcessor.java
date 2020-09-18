package com.acme.edu.client;

import com.acme.edu.connection.NetConnection;
import com.acme.edu.message.ChatMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

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
    public void processMessage(NetConnection clientConnection) throws MessageProcessorException, IOException{
        Reader reader = new Reader(() -> {
            try {
                readFromServer(clientConnection);
            } catch (IOException e) {
                try {
                    throw new MessageProcessorException("Something wrong with connection", e);
                } catch (MessageProcessorException ex){}
            }
        });
        reader.start();
        while (clientConnection.isConnected()) {
            inputMessage = br.readLine();
            ChatMessage chatMessage = new ChatMessage(inputMessage, client.getUserId(), System.currentTimeMillis());

            if (chatMessage.isCommandAvailiable()) {
                sendMessageToServer(chatMessage.toJSON(), clientConnection);
                if ("/exit".equals(chatMessage.getMessageType())) {
                    reader.interrupt();
                    clientConnection.close();
                    break;
                }
                if (chatMessage.getChangedId()) {
                    client.setUserId(chatMessage.getChid());
                    System.out.println("UserId successfully changed");
                    continue;
                }
                if (chatMessage.isChangedRoom()) {
                    client.setRoomId(chatMessage.getRoom());
                    System.out.println("Room successfully changed");
                    continue;
                }
            } else {
                System.out.println("Wrong Command! Try again");
                continue;
            }

            clientConnection.getOutput().flush();
        }
        br.close();
    }

    private void readFromServer(NetConnection clientConnection) throws IOException {
        while (clientConnection.getInput().available() > 0) {
            System.out.println(clientConnection.getInput().readUTF());
        }
    }

    private static void sendMessageToServer(String jsonMessage, NetConnection clientConnection) throws IOException {
        clientConnection.getOutput().writeUTF(jsonMessage);
    }

}

class Reader extends Thread {
    private Thread actualWorker;
    private AtomicBoolean running = new AtomicBoolean(false);

    public Reader(Runnable target) {
        this.actualWorker = new Thread(target);
    }

    public void interrupt() {
        running.set(false);
        actualWorker.interrupt();
    }

    public void run() {
        running.set(true);
        while (running.get()) {
            actualWorker.run();
        }
    }
}
