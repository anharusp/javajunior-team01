package com.acme.edu.server.strategy;

import com.acme.edu.connection.NetConnection;

import java.io.IOException;

public interface Strategy {
    void play(NetConnection clientConnection) throws IOException, ClientExit;
}
