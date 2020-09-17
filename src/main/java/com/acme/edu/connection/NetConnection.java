package com.acme.edu.connection;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;

public class NetConnection implements Closeable{
    private Socket netSocket;
    private DataInputStream input = null;
    private DataOutputStream output = null;

    public NetConnection(@NotNull Socket netSocket) throws IOException {
        this.netSocket = netSocket;
        input = new DataInputStream(
                new BufferedInputStream(
                        netSocket.getInputStream()));
        output = new DataOutputStream(
                new BufferedOutputStream(
                        netSocket.getOutputStream()));
    }

    public boolean isConnected() {
        return netSocket.isConnected();
    }

    public DataInputStream getInput() {
        return input;
    }

    public DataOutputStream getOutput() {
        return output;
    }

    @Override
    public void close() throws IOException {
        output.close();
        input.close();
        netSocket.close();
    }
}
