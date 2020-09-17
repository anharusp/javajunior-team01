package com.acme.edu.client;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Client {
    private static String message;
    private static BufferedReader br;

    public static void main(String[] args) {

        try (final Socket connection = new Socket("127.0.0.1", 10_000); //from args
             final DataInputStream input = new DataInputStream(
                     new BufferedInputStream(
                             connection.getInputStream()));
             final DataOutputStream out = new DataOutputStream(
                     new BufferedOutputStream(
                             connection.getOutputStream()));
        ) {
            br = new BufferedReader(new InputStreamReader(System.in));

            while(connection.isConnected()) {
                message = br.readLine();
                if (CommandParser.isKnownCommand(message))
                    out.writeUTF(decorateMessage(message));
                else {
                    System.out.println("Wrong Command! Try again");
                    continue;
                }
                out.flush();
                System.out.println(">> " + input.readUTF()); //server logging???
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}