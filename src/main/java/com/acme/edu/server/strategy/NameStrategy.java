package com.acme.edu.server.strategy;

import com.acme.edu.connection.NetConnection;
import com.acme.edu.message.ChatMessage;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public class NameStrategy implements Strategy {

    private final String chid;
    private final boolean isChanged;

    public NameStrategy(ChatMessage message) {
        chid = message.getChid();
        isChanged = message.getChangedId();
    }

    @Override
    public void play(NetConnection clientConnection, Set<NetConnection> netConnectionSet, Map<String, NetConnection> nameToConnection) throws IOException, ClientExit {
        System.out.println("here");
        if (isChanged && !nameToConnection.containsKey(chid)) {
            netConnectionSet.remove(clientConnection);
            if (nameToConnection.containsValue(clientConnection)) {
                final String[] previousName = new String[1];
                nameToConnection.forEach((String key, NetConnection value) -> {
                    if (value.equals(clientConnection))
                        previousName[0] = key;
                });
                nameToConnection.remove(previousName[0]);
            }
            nameToConnection.put(chid, clientConnection);
            netConnectionSet.add(clientConnection);
            clientConnection.getOutput().writeUTF("/true");
        }
        else {
            clientConnection.getOutput().writeUTF("/false");
        }
        clientConnection.getOutput().flush();
    }
}
