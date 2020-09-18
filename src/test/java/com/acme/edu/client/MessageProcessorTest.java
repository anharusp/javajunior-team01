package com.acme.edu.client;

import com.acme.edu.connection.NetConnection;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageProcessorTest {

    NetConnection connection = mock(NetConnection.class);
    ClientEntity client = mock(ClientEntity.class);
    BufferedReader br = mock(BufferedReader.class);

    @Test
    public void shouldProcessMessageWhileConnectionIsOpened() throws IOException {
        String message = "/test testMessage";
        MessageProcessor processor = new MessageProcessor(connection, client);
        MessageProcessor.setBr(br);

        when(connection.isConnected()).thenReturn(true).thenReturn(false);
        when(br.readLine()).thenReturn(message);
        processor.processMessage(connection);

        assertThat("Wrong Command! Try again");
    }

    @Test
    public void shouldNotProcessMessageWhileConnectionIsClosed() throws IOException {
        String message = "/test testMessage";
        MessageProcessor processor = new MessageProcessor(connection, client);
        MessageProcessor.setBr(br);

        when(connection.isConnected()).thenReturn(false);
        when(br.readLine()).thenReturn(message);
        processor.processMessage(connection);

        assertThat("");
    }


}
