package com.acme.edu.server;

import com.acme.edu.message.ChatMessage;
import com.google.gson.Gson;
import main.java.com.acme.edu.server.MessageGetter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static Logger logger;

    static {
        try {
            logger = new Logger();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Server() throws FileNotFoundException {

    }

    public static void main(String[] args) {
        Gson gson = new Gson();

        try (final ServerSocket connectionPortListener = new ServerSocket(10_000);
             final Socket clientConnection = connectionPortListener.accept();
             final DataInputStream input = new DataInputStream(
                     new BufferedInputStream(
                             clientConnection.getInputStream()));
             final DataOutputStream out = new DataOutputStream(
                     new BufferedOutputStream(
                             clientConnection.getOutputStream()))) {
            while(clientConnection.isConnected()) {
                String json = input.readUTF();

                ChatMessage message = gson.fromJson(json, ChatMessage.class);

                if ("/hist".equals(message.getMessageType())) {
                    logger.getHistory().forEach(s -> {
                        try {
                            out.writeUTF(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
                else if ("/snd".equals(message.getMessageType())) {
                    logger.log(message);
                    out.writeUTF("Received Message " + message);
                    out.flush();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}