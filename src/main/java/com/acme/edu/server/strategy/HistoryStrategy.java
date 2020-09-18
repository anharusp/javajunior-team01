package com.acme.edu.server.strategy;

import com.acme.edu.connection.NetConnection;
import com.acme.edu.message.ChatMessage;
import com.acme.edu.server.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class implementing {@code /hist} command
 */

public class HistoryStrategy implements Strategy {


    private final String room;
    private final Integer page;

    public HistoryStrategy(ChatMessage message) {
        room = message.getRoom();
        page = message.getHistNumber();
    }

    @Override
    public void play(NetConnection clientConnection, Set<NetConnection> netConnectionSet, Map<String, NetConnection> nameToConnection) throws IOException {
        Logger logger = new Logger(room);
        DataOutputStream output = clientConnection.getOutput();
        List<String> res = logger.getHistory(page);
        res.forEach(s -> {
            try {
                output.writeUTF(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        output.flush();
        logger.close();
    }
}
