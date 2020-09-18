package com.acme.edu.server;

import com.acme.edu.message.ChatMessage;
import com.acme.edu.server.saver.FileSaver;
import com.acme.edu.server.saver.Saver;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Log received messages from Client to {@code history.log} file
 */
public class Logger {
    Saver saver;

    public Logger() throws IOException {
        saver = new FileSaver("history.log");
    }

    public Logger(Saver customSaver) {
        saver = customSaver;
    }

    public Logger(String room) throws IOException {
        saver = new FileSaver("history" + room + ".log");
    }

    public void log(@NotNull ChatMessage msg) throws IOException {
        saver.save(msg);
    }

    public List<String> getHistory(String room) throws IOException {
        ArrayList<String> result = new ArrayList<>();
        try (BufferedReader fileIn =
                     new BufferedReader(new FileReader("history_" + room + ".log"))) {
            while (fileIn.ready()) {
                String tmp = fileIn.readLine();
                result.add(tmp);
            }
        }
        return result;
    }

    public void close() throws IOException {
        saver.close();
    }

}
