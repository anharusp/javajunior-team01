package com.acme.edu.client;

import com.acme.edu.server.command.CommandParser;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {
    private static String message;
    private static BufferedReader br;

    public static void main(String[] args) {
        try (final Socket connection = new Socket("127.0.0.1", 10_000);
             final DataInputStream input = new DataInputStream(
                     new BufferedInputStream(
                             connection.getInputStream()));
             final DataOutputStream out = new DataOutputStream(
                     new BufferedOutputStream(
                             connection.getOutputStream()));
        ) {

            while(connection.isConnected()) {
                message = br.readLine();
                if (CommandParser.isKnownCommand(message))
                    out.writeUTF(decorateMessage(message));
                else {
                    System.out.println("Wrong Command! Try again");
                    continue;
                }
                out.flush();
                System.out.println(">> " + input.readUTF());
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String decorateMessage(String message) {
        long currentDateTime = System.currentTimeMillis();
        Date currentDate = new Date(currentDateTime);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return df.format(currentDate) + ": " + message;
    }
}