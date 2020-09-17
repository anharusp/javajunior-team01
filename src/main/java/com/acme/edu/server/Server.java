package com.acme.edu.server;

import main.java.com.acme.edu.server.MessageGetter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try (final ServerSocket connectionPortListener = new ServerSocket(10_000);
             final Socket clientConnection = connectionPortListener.accept();
             final DataInputStream input = new DataInputStream(
                     new BufferedInputStream(
                             clientConnection.getInputStream()));
             final DataOutputStream out = new DataOutputStream(
                     new BufferedOutputStream(
                             clientConnection.getOutputStream()))) {

            MessageGetter getter = new MessageGetter();
            getter.getMessage(input, out);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}