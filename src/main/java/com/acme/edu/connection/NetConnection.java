package com.acme.edu.connection;

import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.Socket;

/**
 * The {@code NetConnection} class represents connection between Server and Client using
 * sockets and implements Closeable interface
 */

public class NetConnection implements Closeable{
    private Socket netSocket;
    private DataInputStream input = null;
    private DataOutputStream output = null;

    /**
     * Constructor for creation the NetConnection instance and
     * initialization streams for data exchange
     * @param netSocket is notNull instance of {@code Socket} class
     * @throws IOException
     */

    public NetConnection(@NotNull Socket netSocket) throws IOException {
        this.netSocket = netSocket;
        input = new DataInputStream(
                new BufferedInputStream(
                        netSocket.getInputStream()));
        output = new DataOutputStream(
                new BufferedOutputStream(
                        netSocket.getOutputStream()));
    }

    /**
     * Checker for connection state
     * @return {@code boolean}
     */
    public boolean isConnected() {
        return netSocket.isConnected();
    }

    /**
     * Getter for input stream
     * @return {@code DataInputStream} input stream
     */
    public DataInputStream getInput() {
        return input;
    }

    /**
     * Getter for output stream
     * @return {@code DataOutputStream} output stream
     */
    public DataOutputStream getOutput() {
        return output;
    }

    /**
     * Method for closing connections
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        output.close();
        input.close();
        netSocket.close();
    }
}
