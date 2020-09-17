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
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class Server {

    public static void main(String[] args)  {
        ExecutorService pool = newFixedThreadPool(100);
        Thread shutdownHook = new Thread(() -> pool.shutdownNow());
        Runtime.getRuntime().addShutdownHook(shutdownHook);
        try (final ServerSocket connectionPortListener = new ServerSocket(10_000)){
            while (true) {
                NetConnection clientConnection = new NetConnection(connectionPortListener.accept());
                ClientServer clientServer = new ClientServer(clientConnection);
                pool.submit(new ClientServer(clientConnection));
                clientConnection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


class ClientServer extends Thread {
    NetConnection netConnection;

    public ClientServer(NetConnection netConnection) {
        this.netConnection = netConnection;
    }

    private void serveClient(NetConnection clientConnection) throws IOException {
        Gson gson = new Gson();
        DataInputStream input = clientConnection.getInput();
        while(clientConnection.isConnected()) {
            String json = input.readUTF();
            ChatMessage message = gson.fromJson(json, ChatMessage.class);
            try {
                Strategy strategy = StrategyBuilder.create(message);
                strategy.play(clientConnection);
            } catch (UnrecognizedStrategyException e) {
                e.printStackTrace();
            } catch (ClientExit clientExit) {
                break;
            }
        }
    }

    @Override
    public void run() {
        try {
            serveClient(netConnection);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}