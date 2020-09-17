package com.acme.edu;

import com.acme.edu.server.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class ServerTest implements SysoutCaptureAndAssertionAbility{
    @Before
    public void setUpSystemOut() throws IOException {
        resetOut();
        captureSysout();
    }

    @After
    public void tearDown() {
        resetOut();
    }
    //endregion

    @Test
    public void shouldRun() {
        System.out.println("hi");
        assertSysoutContains("hi");
    }
}
