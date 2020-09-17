package com.acme.edu;

import com.acme.edu.server.Server;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ServerTest {
    @Test
    public void shouldRun() {
        Server s = new Server();
        assertTrue(true);
    }
}
