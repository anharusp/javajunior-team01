package com.acme.edu.server.saver;


import com.acme.edu.message.ChatMessage;

import java.io.IOException;

/**
 * Interface which allows implementing saving messages to file
 */
public interface Saver {
    void save(ChatMessage msg) throws IOException;

    String getFileName();

    void close() throws IOException;
}
