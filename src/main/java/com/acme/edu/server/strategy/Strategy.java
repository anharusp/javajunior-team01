package com.acme.edu.server.strategy;

import com.acme.edu.connection.NetConnection;

import java.io.IOException;
import java.util.Set;

public interface Strategy {
    void play(NetConnection clientConnection, Set<NetConnection> netConnectionSet) throws IOException, ClientExit;
}
