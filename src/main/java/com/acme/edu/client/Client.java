package com.acme.edu.client;

import com.acme.edu.connection.NetConnection;
import com.acme.edu.message.ChatMessage;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.Random;

public class Client {
    private static String message;
    private static BufferedReader br;
    private static String chid;

    public static void main(String[] args) {
        Gson gson = new Gson();
        chid = createRandomUserId();
        try (final Socket connection = new Socket("127.0.0.1", 10_000);
        ) {
            NetConnection clientConnection = new NetConnection(connection);
            br = new BufferedReader (new InputStreamReader(System.in));
            while(connection.isConnected()) {
                message = br.readLine();

                ChatMessage chatMessage = new ChatMessage(message, chid, System.currentTimeMillis());
                if (!chid.equals(chatMessage.getChid())) {
                    chid = chatMessage.getChid();
                    System.out.println("Id successfully changed");
                } else {
                    if (chatMessage.isCommandAvailiable()){
                        clientConnection.getOutput().writeUTF(gson.toJson(chatMessage));
                    }
                    else {
                        System.out.println("Wrong Command! Try again");
                        continue;
                    }
                    if ("/exit".equals(chatMessage.getMessageType())) {
                        clientConnection.getOutput().writeUTF(gson.toJson(chatMessage));
                        clientConnection.close();
                        break;
                    }
                    clientConnection.getOutput().flush();
                    System.out.println(clientConnection.getInput().readUTF());
                    while (clientConnection.getInput().available() > 0) {
                        System.out.println(clientConnection.getInput().readUTF());
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String createRandomUserId() {
        Random rand = new Random();
        return "id" + rand.nextInt(10000000);
    }
}