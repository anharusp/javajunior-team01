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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class Server {

    private static Set<NetConnection> netConnectionSet;

    public static void main(String[] args) {
        ExecutorService pool = newFixedThreadPool(100);
        Thread shutdownHook = new Thread(() -> pool.shutdownNow());
        Runtime.getRuntime().addShutdownHook(shutdownHook);

        netConnectionSet = Collections.synchronizedSet(new HashSet<NetConnection>());
        try (final ServerSocket connectionPortListener = new ServerSocket(10_000)) {
            while (true) {
                makeAndServeConnection(connectionPortListener, pool);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void makeAndServeConnection(ServerSocket connectionPortListener, ExecutorService pool) {
        NetConnection clientConnection = null;
        try {
            clientConnection = new NetConnection(connectionPortListener.accept());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (clientConnection != null) {
            netConnectionSet.add(clientConnection);
            Thread clientThread = new ClientServer(clientConnection, netConnectionSet);
            clientThread.start();
        }
    }
}


class ClientServer extends Thread {
    private Set<NetConnection> netConnectionSet;
    NetConnection netConnection;

    public ClientServer(NetConnection netConnection, Set<NetConnection> netConnectionSet) {
        this.netConnection = netConnection;
        this.netConnectionSet = netConnectionSet;
    }

    private void serveClient(NetConnection clientConnection, Set<NetConnection> netConnectionSet) throws IOException {
        Gson gson = new Gson();
        DataInputStream input = clientConnection.getInput();
        while(clientConnection.isConnected()) {
            String json = input.readUTF();
            ChatMessage message = gson.fromJson(json, ChatMessage.class);
            try {
                Strategy strategy = StrategyBuilder.create(message);
                strategy.play(clientConnection, netConnectionSet);
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
            serveClient(netConnection, netConnectionSet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            netConnection.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        netConnectionSet.remove(netConnection);
    }
}