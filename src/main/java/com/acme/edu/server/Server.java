package com.acme.edu.server;

import com.acme.edu.connection.NetConnection;
import com.acme.edu.message.ChatMessage;
import com.acme.edu.server.strategy.ClientExit;
import com.acme.edu.server.strategy.Strategy;
import com.acme.edu.server.strategy.StrategyBuilder;
import com.acme.edu.server.strategy.UnrecognizedStrategyException;
import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.util.List;

public class Server {

    public static void main(String[] args)  {
        try (final ServerSocket connectionPortListener = new ServerSocket(10_000);
             final NetConnection clientConnection = new NetConnection(connectionPortListener.accept())) {
            serveClient(clientConnection);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void serveClient(NetConnection clientConnection) throws IOException {
        Gson gson = new Gson();
        DataInputStream input = clientConnection.getInput();
        DataOutputStream output = clientConnection.getOutput();
        while(clientConnection.isConnected()) {
            String json = input.readUTF();
            ChatMessage message = gson.fromJson(json, ChatMessage.class);
            try {
                Strategy strategy = StrategyBuilder.create(message);
                strategy.play(clientConnection);
            } catch (UnrecognizedStrategyException e) {
                e.printStackTrace();
            } catch (ClientExit clientExit) {
                clientExit.printStackTrace();
                break;
            }
        }
    }
}