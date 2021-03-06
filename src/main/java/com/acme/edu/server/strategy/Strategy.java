package com.acme.edu.server.strategy;

import com.acme.edu.connection.NetConnection;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Common interface for implementing commands
 */

public interface Strategy {
    void play(NetConnection clientConnection, Set<NetConnection> netConnectionSet, Map<String, NetConnection> nameToConnection) throws IOException, ClientExit;
}
