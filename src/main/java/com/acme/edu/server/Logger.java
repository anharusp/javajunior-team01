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
    ArrayList<Saver> savers;
    int current;
    String room;

    public Logger(String room) throws IOException {
        savers = new ArrayList<>();
        savers.add(0, new FileSaver("history_" + room + "_0.log"));
        current = 0;
        this.room = room;
    }
    public Logger(Saver saver, String room) throws IOException {
        savers = new ArrayList<>(2);
        savers.add(0, saver);
        current = 0;
        this.room = room;
    }

    public void log(@NotNull ChatMessage msg) throws IOException {
        FileSaver tmp = (FileSaver) savers.get(current);
        tmp.save(msg);
        if (tmp.lines > 20)
            savers.add(++current, new FileSaver("history_"+ room +"_" + current + ".log"));
    }

    public List<String> getHistory(int page) throws IOException {
        ArrayList<String> result = new ArrayList<>();
        if (page == -1)
            return getAllHistory();
        FileSaver tmp = (FileSaver) savers.get(page);
        try (BufferedReader fileIn =
                     new BufferedReader(new FileReader(tmp.getFileName()))) {
            while (fileIn.ready()) {
                String temp = fileIn.readLine();
                result.add(temp);
            }
        }
        return result;
    }

    List<String> getAllHistory() throws IOException {
        ArrayList<String> result = new ArrayList<>();
        for(int i = 0; i <= current; i++) {
            FileSaver tmp = (FileSaver) savers.get(i);
            result.addAll(getHistory(i));
        }
        return result;
    }


    public void close() throws IOException {
        savers.forEach(s -> {
            try {
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}
