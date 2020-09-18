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

    public static void main(String[] args) {
        try (final Socket connection = new Socket("127.0.0.1", 10_000))
        {
            NetConnection clientConnection = new NetConnection(connection);

            MessageProcessor processor = new MessageProcessor(clientConnection, client);
            processor.processMessage(clientConnection);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}