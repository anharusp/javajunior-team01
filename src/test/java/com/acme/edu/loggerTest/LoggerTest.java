package com.acme.edu.loggerTest;

import com.acme.edu.message.ChatMessage;
import com.acme.edu.server.Logger;
import com.acme.edu.server.saver.FileSaver;
import com.acme.edu.server.saver.Saver;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class LoggerTest {

    @Test
    public void shouldCreateLoggerForRoomName() {
    }

    @Test
    public void shouldLogMessage() throws IOException {
        Saver mock = mock(Saver.class);
        ChatMessage mockMessage = mock(ChatMessage.class);
        Logger logger = new Logger(mock);
        logger.log(mockMessage);
        verify(mock).save(mockMessage);
    }

    @Test
    public void shouldGetHistoryWhenFileEmpty() throws IOException {
        Logger logger = new Logger("default");
        assertThat(logger.getHistory("default").isEmpty());
        logger.close();
    }

    @Test
    public void shouldGetHistoryWhenLoggerLogged() throws IOException {
        Logger logger = new Logger("default");
        ChatMessage mock = mock(ChatMessage.class);
        logger.log(mock);
        assertThat(logger.getHistory("default").contains(mock));
        logger.close();
    }
}

