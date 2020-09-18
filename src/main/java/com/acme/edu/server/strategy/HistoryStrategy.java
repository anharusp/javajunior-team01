package com.acme.edu.server.strategy;

import com.acme.edu.connection.NetConnection;
import com.acme.edu.server.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

public class HistoryStrategy implements Strategy {
    @Override
    public void play(NetConnection clientConnection, Set<NetConnection> netConnectionSet) throws IOException {
        Logger logger = new Logger();
        DataInputStream input = clientConnection.getInput();
        DataOutputStream output = clientConnection.getOutput();
        List<String> res = logger.getHistory();
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
