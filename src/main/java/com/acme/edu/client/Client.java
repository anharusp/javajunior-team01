package com.acme.edu.client;

import java.io.*;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {
    public static void main(String[] args) {
        try (final Socket connection = new Socket("127.0.0.1", 10_000);
             final DataInputStream input = new DataInputStream(
                     new BufferedInputStream(
                             connection.getInputStream()));
             final DataOutputStream out = new DataOutputStream(
                     new BufferedOutputStream(
                             connection.getOutputStream()));
        ) {

            System.out.println(connection.getLocalPort());
            out.writeUTF("HW!!!"); //LOG INT 5
            out.flush();
            System.out.println(">> " + input.readUTF());

            /*
            zamestitel.log(5);
            zamestitel.flush();
            zamestitel.close();
            */

            String test = decorateMessage("hi");
            System.out.println(test);
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