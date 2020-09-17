package com.acme.edu.server;

import main.java.com.acme.edu.server.MessageGetter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static Logger logger;

    public Server() throws FileNotFoundException {
        logger = new Logger();
    }

    public static void main(String[] args) {
        try (final ServerSocket connectionPortListener = new ServerSocket(10_000);
             final Socket clientConnection = connectionPortListener.accept();
             final DataInputStream input = new DataInputStream(
                     new BufferedInputStream(
                             clientConnection.getInputStream()));
             final DataOutputStream out = new DataOutputStream(
                     new BufferedOutputStream(
                             clientConnection.getOutputStream()))) {
            while(clientConnection.isConnected()) {
                MessageGetter getter = new MessageGetter();
                getter.getMessage(input, out);

                if (messa) {
                    logger.getHistory().forEach(s -> {
                        try {
                            out.writeUTF(s);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
                else {
                    logger.log(message);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}