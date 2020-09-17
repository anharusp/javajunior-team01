package com.acme.edu.server.strategy;

import com.acme.edu.connection.NetConnection;
import com.acme.edu.message.ChatMessage;
import com.acme.edu.server.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SendStrategy implements Strategy {
    private final ChatMessage message;

    public SendStrategy(ChatMessage message) {
        this.message = message;
    }

    @Override
    public void play(NetConnection clientConnection) throws IOException {
        Logger logger = new Logger();
        DataInputStream input = clientConnection.getInput();
        DataOutputStream output = clientConnection.getOutput();
        logger.log(message);
        output.writeUTF(message.toString());
        output.flush();
        logger.close();
    }
}
