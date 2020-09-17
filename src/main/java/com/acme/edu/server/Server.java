package com.acme.edu.server;

import com.acme.edu.message.ChatMessage;
import com.google.gson.Gson;
import main.java.com.acme.edu.server.MessageGetter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {

    public static void main(String[] args)  {
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
                Logger logger = new Logger();
                ChatMessage message = gson.fromJson(json, ChatMessage.class);

                if ("/exit".equals(message.getMessageType())) {
                    logger.log(message);
                    out.writeUTF(message.toString());
                    out.flush();
                    break;
                }

                if ("/hist".equals(message.getMessageType())) {
                    List<String> res = logger.getHistory();
                    res.forEach(s -> {
                        try {
                            out.writeUTF(s);
                            out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
                else if ("/snd".equals(message.getMessageType())) {
                    logger.log(message);
                    out.writeUTF(message.toString());
                    out.flush();
                }
                logger.close();
          }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}