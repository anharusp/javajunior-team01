package com.acme.edu.server.saver;


import com.acme.edu.message.ChatMessage;

import java.io.IOException;

public interface Saver {
    void save(ChatMessage msg) throws IOException;

    String getFileName();
}
