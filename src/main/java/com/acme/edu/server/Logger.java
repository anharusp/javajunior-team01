package com.acme.edu.server;

import com.acme.edu.message.ChatMessage;
import com.acme.edu.server.saver.FileSaver;
import com.acme.edu.server.saver.Saver;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Logger {
    Saver saver;

    public Logger() throws IOException {
        saver = new FileSaver("history.log");
    }

    public void log(ChatMessage msg) throws IOException {
        saver.save(msg);
    }

    public List<String> getHistory() throws IOException {
        ArrayList<String> result = new ArrayList<>();
        try (BufferedReader fileIn =
                     new BufferedReader(new FileReader(saver.getFileName()))) {
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
