package com.acme.edu.server.strategy;

import com.acme.edu.connection.NetConnection;
import com.acme.edu.message.ChatMessage;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class PrivateSendStrategy implements Strategy{

    public PrivateSendStrategy(ChatMessage message) {

    }

    @Override
    public void play(NetConnection clientConnection, Set<NetConnection> netConnectionSet, Map<String, NetConnection> nameToConnection) throws IOException, ClientExit {

    }
}
