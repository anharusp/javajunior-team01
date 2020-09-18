package com.acme.edu.client;

import com.acme.edu.connection.NetConnection;
import java.io.*;
import java.net.Socket;

/**
 * Main class for Client side.
 * @author Gavrilova D., Kharitonova A., Yusufov Yu., Belyaeva A.
 */

public class Client {
    private static ClientEntity client = new ClientEntity();
    private static boolean connectionOpen = false;

    public static void main(String[] args) {
        while (!connectionOpen) {
            try (final Socket connection = new Socket("127.0.0.1", 10_000)) {
                System.out.println("Connection with server successfully established");
                connectionOpen = true;
                NetConnection clientConnection = new NetConnection(connection);

                MessageProcessor processor = new MessageProcessor(clientConnection, client);
                processor.processMessage(clientConnection);
            } catch (IOException e) {
                System.out.println("Connection with server is lost, please, wait");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException interruptedException) {
                    interruptedException.printStackTrace();
                }
                //e.printStackTrace();
            }
            connectionOpen =false;
        }
    }

}