package com.acme.edu.server.strategy;

import com.acme.edu.connection.NetConnection;
import com.acme.edu.message.ChatMessage;
import com.acme.edu.server.Logger;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Class implementing {@code /snd} command
 */
public class SendStrategy implements Strategy {
    private final ChatMessage message;

    public SendStrategy(ChatMessage message) {
        this.message = message;
    }

    @Override
    public void play(NetConnection clientConnection, Set<NetConnection> netConnectionSet, Map<String, NetConnection> nameToConnection) throws IOException {
        Logger logger = new Logger(message.getRoom());
        netConnectionSet.forEach((connection) -> {
            DataOutputStream output = connection.getOutput();
            try {
                output.writeUTF(message.toString());
                output.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        logger.log(message);
        logger.close();
    }
}
