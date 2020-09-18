package com.acme.edu.server.strategy;

import com.acme.edu.connection.NetConnection;
import com.acme.edu.message.ChatMessage;

import java.io.IOException;
import java.util.Set;

public class PrivateSendStrategy implements Strategy{
    public PrivateSendStrategy(ChatMessage message) {
    }

    @Override
    public void play(NetConnection clientConnection, Set<NetConnection> netConnectionSet) throws IOException, ClientExit {
        
    }
}
