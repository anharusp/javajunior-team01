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
        File testFile = new File("test.log");
        Logger logger = new Logger(new FileSaver(testFile));
        assertThat(logger.getHistory().isEmpty());
        logger.close();
        testFile.delete();
    }

    @Test
    public void shouldGetHistoryWhenLoggerLogged() throws IOException {
        File testFile = new File("test.log");
        Logger logger = new Logger(new FileSaver(testFile));
        ChatMessage mock = mock(ChatMessage.class);
        logger.log(mock);
        assertThat(logger.getHistory().contains(mock));
        logger.close();
        testFile.delete();
    }
}

