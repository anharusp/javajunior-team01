package com.acme.edu.server.saver;


import java.io.IOException;

public interface Saver {
    void save(ChatMessage msg) throws IOException;

    String getFileName();
}
