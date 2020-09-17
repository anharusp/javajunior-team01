package com.acme.edu.connection;

import java.io.*;
import java.net.Socket;

public class NetConnection implements Closeable{
    Socket netSocket;
    private DataInputStream input = null;
    private DataOutputStream output = null;

    public NetConnection(Socket netSocket) throws IOException {
        this.netSocket = netSocket;
        input = new DataInputStream(
                new BufferedInputStream(
                        netSocket.getInputStream()));
        output = new DataOutputStream(
                new BufferedOutputStream(
                        netSocket.getOutputStream()));
    }

    @Override
    public void close() throws IOException {
        output.close();
        input.close();
        netSocket.close();
    }
}
