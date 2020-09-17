package com.acme.edu.server.saver;

import java.io.*;

public class FileSaver implements Saver {
    String fileName;
    boolean append = true;
    private final BufferedWriter bufferedWriter;

    public FileSaver(String s) throws FileNotFoundException {
        fileName = s;
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName,  append)));
    }

    public  FileSaver(String s, boolean a) throws FileNotFoundException {
        fileName = s;
        append = a;
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName,  append)));
    }

    @Override
    public void save(ChatMessage msg) throws IOException {
        bufferedWriter.write(msg.toString());
        bufferedWriter.newLine();
        //bufferedWriter.flush();
    }

    public String getFileName() {
        return fileName;
    }
}
