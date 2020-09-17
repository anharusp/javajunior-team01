package com.acme.edu.client;

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
        Random rand = new Random();
        chid = "id" + rand.nextInt(10000000);
        try (final Socket connection = new Socket("127.0.0.1", 10_000);
             final DataInputStream input = new DataInputStream(
                     new BufferedInputStream(
                             connection.getInputStream()));
             final DataOutputStream out = new DataOutputStream(
                     new BufferedOutputStream(
                             connection.getOutputStream()));
        ) {
            br = new BufferedReader (new InputStreamReader(System.in));
            while(connection.isConnected()) {
                message = br.readLine();

                ChatMessage chatMessage = new ChatMessage(message, chid, System.currentTimeMillis());
                if (!chid.equals(chatMessage.getChid())) {
                    chid = chatMessage.getChid();
                    System.out.println("Id successfully changed");
                } else {
                    if (chatMessage.isCommandAvailiable()){
                        out.writeUTF(gson.toJson(chatMessage));
                    }
                    else {
                        System.out.println("Wrong Command! Try again");
                        continue;
                    }
                    if ("/exit".equals(chatMessage.getMessageType())) {
                        out.writeUTF(gson.toJson(chatMessage));
                        break;
                    }
                    out.flush();
                    System.out.println(input.readUTF());
                    while (input.available() > 0) {
                        System.out.println(input.readUTF());
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}