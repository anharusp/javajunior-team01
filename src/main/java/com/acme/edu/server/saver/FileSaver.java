package com.acme.edu.server.saver;

import com.acme.edu.message.ChatMessage;

import java.io.*;

public class FileSaver implements Saver {
    String fileName;
    boolean append = true;
    private final OutputStreamWriter Writer;

    public FileSaver(String s) throws IOException {
        fileName = s;
        this.Writer = new OutputStreamWriter(new FileOutputStream(fileName,  append));
    }

    public  FileSaver(String s, boolean a) throws FileNotFoundException {
        fileName = s;
        append = a;
        this.Writer = new OutputStreamWriter(new FileOutputStream(fileName,  append));
    }

    @Override
    public void save(ChatMessage msg) throws IOException {
       Writer.write(msg.toString() + System.lineSeparator());

    }

    public String getFileName() {
        return fileName;
    }

    public void close() throws IOException {
        Writer.close();
    }
}
