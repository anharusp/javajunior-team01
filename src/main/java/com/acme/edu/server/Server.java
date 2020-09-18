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
import java.util.*;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

public class Server {

    private static Set<NetConnection> netConnectionSet;
    private static Map<String, NetConnection> mapNameToConnection;

    public static void main(String[] args) {
        ExecutorService pool = newFixedThreadPool(100);
        Thread shutdownHook = new Thread(() -> pool.shutdownNow());
        Runtime.getRuntime().addShutdownHook(shutdownHook);

        netConnectionSet = Collections.synchronizedSet(new HashSet<NetConnection>());
        mapNameToConnection = Collections.synchronizedMap(new HashMap<>());
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
            Thread clientThread = new ClientServer(clientConnection, netConnectionSet, mapNameToConnection);
            clientThread.start();
        }
    }
}


class ClientServer extends Thread {
    private Set<NetConnection> netConnectionSet;
    NetConnection netConnection;
    private Map<String, NetConnection> nameToConnection;

    public ClientServer(NetConnection netConnection, Set<NetConnection> netConnectionSet, Map<String, NetConnection> nameToConnection) {
        this.netConnection = netConnection;
        this.netConnectionSet = netConnectionSet;
        this.nameToConnection = nameToConnection;
    }

    private void serveClient(NetConnection clientConnection, Set<NetConnection> netConnectionSet, Map<String, NetConnection> nameToConnection) throws IOException {
        Gson gson = new Gson();
        DataInputStream input = clientConnection.getInput();
        while(clientConnection.isConnected()) {
            String json = input.readUTF();
            ChatMessage message = gson.fromJson(json, ChatMessage.class);
            //nameToConnection.put(message.getChid(), clientConnection);
            try {
                Strategy strategy = StrategyBuilder.create(message);
                strategy.play(clientConnection, netConnectionSet, nameToConnection);
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
            serveClient(netConnection, netConnectionSet, nameToConnection);
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